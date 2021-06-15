package com.bakafulteam.weplan.controllers;

import com.bakafulteam.weplan.domains.*;
import com.bakafulteam.weplan.repositories.UserRepository;
import com.bakafulteam.weplan.repositories.TaskRepository;
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
import org.supercsv.io.CsvBeanReader;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanReader;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Controller
public class TaskController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    TaskRepository taskRepository;

    User user;

    @GetMapping("/create-task")
    public String CreateTask(Model model1, Model model2, @RequestParam String eventType) {
        switch (eventType) {
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

    @PostMapping("/create-simple-task")
    public String CreatesimpleTask(SimpleTask task, @AuthenticationPrincipal WePlanUserDetails userInfo) {
        user = userRepository.findByEmail(userInfo.getEmail());
        task.setTaskOwner(user);
        taskRepository.save(task);
        return "redirect:/user-area";
    }

    @PostMapping("/create-teams-task")
    public String CreateTeamsTask(TeamsTask task, @AuthenticationPrincipal WePlanUserDetails userInfo) {
        user = userRepository.findByEmail(userInfo.getEmail());
        task.getCollaborators().add(user);
        taskRepository.save(task);
        return "redirect:/user-area";
    }

    @PostMapping("/create-scheduled-task")
    public String CreateScheduledTask(ScheduledTask task, @AuthenticationPrincipal WePlanUserDetails userInfo) {
        user = userRepository.findByEmail(userInfo.getEmail());
        task.setEventOwner(user);
        task.setTimeString(task.getStartTime().toString() + " - " +
                task.getEndTime().toString());
        taskRepository.save(task);
        return "redirect:/user-area";
    }

    @PostMapping("/user-area/add-collaborator")
    public String addCollaborator(@RequestParam String collaboratorUsername, @RequestParam Long taskId){
        Optional<Task> taskOptional = taskRepository.findById(taskId);
        Optional<TeamsTask> teamsTaskOptional = taskOptional.map(task -> (TeamsTask) task);
        User collab = userRepository.findByUsername(collaboratorUsername);
        teamsTaskOptional.get().getCollaborators().add(collab);
        taskRepository.save(teamsTaskOptional.get());
        return "redirect:/user-area";
    }

    @GetMapping("/user-area/remove-task")
    public String removeTask(@RequestParam Long taskId) {
        taskRepository.deleteById(taskId);
        return "redirect:/user-area";
    }

    @GetMapping("/user-area/export-timetable")
    public void exportToCSV(HttpServletResponse response, @AuthenticationPrincipal WePlanUserDetails userInfo) throws IOException {

        user = userRepository.findByEmail(userInfo.getEmail());
        response.setContentType("text/csv");
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH..mm..ss");
        String fileName = "tasks_" + user.getUsername() + "_" + dateFormat.format(new Date()) + ".csv";

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename = " + fileName;
        response.setHeader(headerKey, headerValue);

        /*List<Task> userTasks = taskRepository.findAll()
                .stream()
                .map(task -> {
                    if (task.getTaskType().equals("Simple Task")) {
                        SimpleTask simpleTask =
                                Optional.of(task).map(simpleTaskCast -> (SimpleTask) simpleTaskCast).get();
                        if (simpleTask.getTaskOwner().equals(user))
                            return simpleTask;
                        else
                            return null;
                    } else if (task.getTaskType().equals("Teams Task")) {
                        TeamsTask teamsTask =
                                Optional.of(task).map(teamsTaskCast -> (TeamsTask) teamsTaskCast).get();
                        if (teamsTask.getCollaborators().contains(user))
                            return teamsTask;
                        else
                            return null;
                    } else {
                        ScheduledTask scheduledTask =
                                Optional.of(task).map(scheduledTaskCast -> (ScheduledTask) scheduledTaskCast).get();
                        if (scheduledTask.getEventOwner().equals(user))
                            return scheduledTask;
                        else
                            return null;
                    }
                })
                .sorted(Comparator.comparing(Task::getDate)
                        .thenComparing(Task::getTaskTime))
                .collect(Collectors.toList());*/

        List<SimpleTask> simpleTasks = taskRepository.findAll()
                .stream()
                .filter(task -> task.getTaskType().equals("Simple Task"))
                .map(task -> (SimpleTask)task)
                .filter(simpleTask -> simpleTask.getTaskOwner().equals(user))
                .collect(Collectors.toList());

        List<TeamsTask> teamsTasks = taskRepository.findAll()
                .stream()
                .filter(task -> task.getTaskType().equals("Teams Task"))
                .map(task -> (TeamsTask) task)
                .filter(teamsTask -> teamsTask.getCollaborators().contains(user))
                .collect(Collectors.toList());

        List<ScheduledTask> scheduledTasks = taskRepository.findAll()
                .stream()
                .filter(task -> task.getTaskType().equals("Scheduled Task"))
                .map(task -> (ScheduledTask) task)
                .filter(scheduledTask -> scheduledTask.getEventOwner().equals(user))
                .collect(Collectors.toList());

        List<Task> userTasks = new ArrayList<>();
        userTasks.addAll(simpleTasks);
        userTasks.addAll(teamsTasks);
        userTasks.addAll(scheduledTasks);
        userTasks.sort(Comparator.comparing(Task::getDate).thenComparing(Task::getTaskTime));

        System.out.println("uwu" + userTasks);

        ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.STANDARD_PREFERENCE);

        String[] csvHeader = {"Task name", "Description", "Date", "Time", "Task type"};
        String[] nameMapping1 = {"name", "description", "date", "taskTime", "taskType"};
        String[] nameMapping2 = {"name", "description", "date", "timeString", "taskType"};

        csvWriter.writeHeader(csvHeader);

        for(Task task : userTasks) {
            if(task.getTaskType().equals("Scheduled Task"))
                csvWriter.write(task, nameMapping2);
            else csvWriter.write(task, nameMapping1);
        }
        csvWriter.close();
    }

    @GetMapping("/import")
    public String readCSVFile(/*@RequestParam MultipartFile csvFile*/) {

        ICsvBeanReader csvReader = null;
        CellProcessor[] processors = new CellProcessor[] {
                new NotNull(),
                //new org.supercsv.cellprocessor.Optional(),
                //new ParseDate("yyyy-MM-dd"),
                //new ParseDate("HH:mm"),
                new NotNull(),
                new NotNull(),
                new NotNull(),
                new NotNull(),
        };
        try {
            csvReader = new CsvBeanReader(new FileReader("src/tasks_Sebas_2021-06-15_13..30..09.csv"), CsvPreference.STANDARD_PREFERENCE);

            String[] header = {"A", "B", "C", "D", "E"};
            asd task = null;
            while((task = csvReader.read(asd.class, header, processors)) != null) {
                System.out.printf("%s %s %s %s %s" , task.getA(),
                        task.getB(), task.getC(), task.getD(), task.getE());
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
        System.out.println("wenas");
        return "redirect:/user-area";
    }
}
