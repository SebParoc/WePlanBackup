package com.bakafulteam.weplan.controllers;

import com.bakafulteam.weplan.Exceptions.AlreadyAddedCollaboratorException;
import com.bakafulteam.weplan.Exceptions.NonExistentUserException;
import com.bakafulteam.weplan.Exceptions.WrongFileExtensionException;
import com.bakafulteam.weplan.Exceptions.WrongImageExtensionException;
import com.bakafulteam.weplan.domains.*;
import com.bakafulteam.weplan.repositories.UserRepository;
import com.bakafulteam.weplan.repositories.TaskRepository;
import com.bakafulteam.weplan.services.FileUploadService;
import com.bakafulteam.weplan.services.TaskService;
import com.bakafulteam.weplan.user_security.WePlanUserDetails;
import org.hibernate.engine.internal.Nullability;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.supercsv.cellprocessor.ParseDate;
import org.supercsv.cellprocessor.constraint.NotNull;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.*;
import org.supercsv.prefs.CsvPreference;


import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Controller
public class TaskController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    TaskRepository taskRepository;

    User user;

    /**
     * GET http request that receives the parameter taskType to decide which models to send.
     * @param model1
     * @param model2
     * @param taskType
     * @return a string with the name of the view template (html)
     */
    @GetMapping("/create-task")
    public String CreateTask(Model model1, Model model2, @RequestParam String taskType) {
        switch (taskType) {
            case "Simple Task":
                model1.addAttribute("task", new SimpleTask());
                model2.addAttribute("taskType", "simple");
                break;
            case "Teams Task":
                model1.addAttribute("task", new TeamsTask());
                model2.addAttribute("taskType", "teams");
                break;
            case "Scheduled Task":
                model1.addAttribute("task", new ScheduledTask());
                model2.addAttribute("taskType", "scheduled");
                break;
            default:
                break;
        }
        return "TaskManager/CreateTask";
    }

    /**
     * POST http request that saves a Simple Task if such model was sent in the CreateTasks method
     * it sets the current user as the owner
     * @param task
     * @param userInfo
     * @return a redirection to the http request for the user area
     */
    @PostMapping("/create-simple-task")
    public String CreatesimpleTask(SimpleTask task, @AuthenticationPrincipal WePlanUserDetails userInfo) {
        user = userRepository.findByEmail(userInfo.getEmail());
        task.setTaskOwner(user);
        taskRepository.save(task);
        return "redirect:/user-area";
    }

    /**
     * POST http request that saves a Teams Task if such model was sent in the CreateTasks method
     * it adds the current user as a collaborator
     * @param task
     * @param userInfo
     * @return a redirection to the http request for the user area
     */
    @PostMapping("/create-teams-task")
    public String CreateTeamsTask(TeamsTask task, @AuthenticationPrincipal WePlanUserDetails userInfo) {
        user = userRepository.findByEmail(userInfo.getEmail());
        task.getCollaborators().add(user);
        taskRepository.save(task);
        return "redirect:/user-area";
    }

    /**
     * POST http request that saves a Scheduled Task if such model was sent in the CreateTasks method
     * it sets the current user as the owner
     * @param task
     * @param userInfo
     * @return a redirection to the http request for the user area
     */
    @PostMapping("/create-scheduled-task")
    public String CreateScheduledTask(ScheduledTask task, @AuthenticationPrincipal WePlanUserDetails userInfo) {
        user = userRepository.findByEmail(userInfo.getEmail());
        task.setTaskOwner(user);
        task.setTaskTime(task.getStartTime() + " - " + task.getEndTime());
        taskRepository.save(task);
        return "redirect:/user-area";
    }

    /**
     * This method is called only through Teams tasks, it adds a new collaborator depending on the username that
     * the user writes. It throws exceptions in case the input is invalid
     * @param collaboratorUsername
     * @param taskId
     * @return a redirection to the http request for the user area
     */
    @PostMapping("/user-area/add-collaborator")
    public String addCollaborator(@RequestParam String collaboratorUsername, @RequestParam Long taskId){
        Optional<Task> taskOptional = taskRepository.findById(taskId);
        Optional<TeamsTask> teamsTaskOptional = taskOptional.map(task -> (TeamsTask) task);
        User collab = userRepository.findByUsername(collaboratorUsername);

        if(teamsTaskOptional.get().getCollaborators().contains(collab)){
            throw new AlreadyAddedCollaboratorException(collaboratorUsername);
        }
        if(collab==null){
            throw new NonExistentUserException(collaboratorUsername);
        }
        teamsTaskOptional.get().getCollaborators().add(collab);

        taskRepository.save(teamsTaskOptional.get());
        return "redirect:/user-area";
    }

    /**
     * It removes the task from the repository, and therefore, from the database.
     * @param taskId
     * @return a redirection to the http request for the user area
     */
    @GetMapping("/user-area/remove-task")
    public String removeTask(@RequestParam Long taskId) {
        taskRepository.deleteById(taskId);
        return "redirect:/user-area";
    }

    /**
     * POST http request that receives a MultipartFile as a parameter to read it. Only files of type
     * CSV are allowed, otherwise it will throw an Exception. It uses the SuperCSV Java library to map
     * headers, processors, and finally, to create instances of the different subclasses of Task to
     * save them in the repository.
     * @param csvFile
     * @param userInfo
     * @return a redirection to the http request for the user area
     */
    @PostMapping("/user-area/import-timetable")
    public String readCSVFile(@RequestParam MultipartFile csvFile, @AuthenticationPrincipal WePlanUserDetails userInfo) {
        String extension = csvFile.getOriginalFilename().substring(csvFile.getOriginalFilename().indexOf("."));
        if(!extension.equals(".csv"))
            throw new WrongFileExtensionException(extension);

        user = userRepository.findByEmail(userInfo.getEmail());

        ICsvMapReader csvReader = null;
        CellProcessor[] processors = new CellProcessor[] {
                new NotNull(),
                new org.supercsv.cellprocessor.Optional(),
                new NotNull(),
                new NotNull(),
                new NotNull(),
        };

        try {
            FileUploadService.uploadFile(csvFile.getOriginalFilename(), csvFile);
            csvReader = new CsvMapReader(new FileReader(FileUploadService
                    .getFileDirectory(csvFile.getOriginalFilename())), CsvPreference.STANDARD_PREFERENCE);

            String[] header = {"Name", "Description", "Date", "TaskTime", "TaskType"};
            Map<String, Object> taskMap;
            while((taskMap = csvReader.read(header, processors)) != null) {

                if(taskMap.get("TaskType").equals("Simple Task")) {

                    SimpleTask simpleTask = new SimpleTask(taskMap.get("Name").toString(),
                            taskMap.get("Description").toString(), taskMap.get("Date").toString(),
                            taskMap.get("TaskTime").toString(), taskMap.get("TaskType").toString(), user);

                    taskRepository.save(simpleTask);

                } else if (taskMap.get("TaskType").equals("Teams Task")) {

                    TeamsTask teamsTask = new TeamsTask(taskMap.get("Name").toString(),
                            taskMap.get("Description").toString(), taskMap.get("Date").toString(),
                            taskMap.get("TaskTime").toString(), taskMap.get("TaskType").toString());
                    teamsTask.getCollaborators().add(user);

                    taskRepository.save(teamsTask);

                } else if (taskMap.get("TaskType").equals("Scheduled Task")) {

                    ScheduledTask scheduledTask = new ScheduledTask(taskMap.get("Name").toString(),
                            taskMap.get("Description").toString(), taskMap.get("Date").toString(),
                            taskMap.get("TaskTime").toString().substring(0,5),
                            taskMap.get("TaskTime").toString().substring(8),
                            taskMap.get("TaskType").toString(), user);
                    taskRepository.save(scheduledTask);
                }
            }
        } catch (FileNotFoundException ex) {
            System.err.println("Could not find the CSV file: " + ex);
        } catch (IOException ex) {
            System.err.println("Error reading the CSV file: " + ex);
        } finally {
            if (csvReader != null) {
                try {
                    csvReader.close();
                } catch (IOException ex) {
                    System.err.println("Error closing the reader: " + ex);
                }
            }
        }
        return "redirect:/user-area";
    }
}
