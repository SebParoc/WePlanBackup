package com.bakafulteam.weplan.controllers;

import com.bakafulteam.weplan.domains.*;
import com.bakafulteam.weplan.repositories.TaskRepository;
import com.bakafulteam.weplan.repositories.UserRepository;
import com.bakafulteam.weplan.repositories.WePlanFileRepository;
import com.bakafulteam.weplan.services.TaskService;
import com.bakafulteam.weplan.user_security.WePlanUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import java.io.IOException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
public class FileUploadDownloadController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    WePlanFileRepository fileRepository;

    @Autowired
    TaskRepository taskRepository;

    /**
     * POST http request that uploads a file to a Single or Teams Task. The Multipart file is saved
     * as a WePlanFile object with some additional information to be stored in the database. It checks
     * the type of the class and cast the Task object to access their attributes and adds the wePlanFile
     * to their lists.
     * @param file
     * @param taskId
     * @param attributes
     * @param userInfo
     * @return a redirection to the http request for the user area
     * @throws IOException
     */
    @PostMapping("/user-area/upload-file")
    public String uploadFile(@RequestParam("file") MultipartFile file,
                              @RequestParam() Long taskId,
                              RedirectAttributes attributes ,
                              @AuthenticationPrincipal WePlanUserDetails userInfo) throws IOException {

        String fileName =  file.getOriginalFilename();
        WePlanFile wePlanFile = new WePlanFile();
        wePlanFile.setName(fileName);
        wePlanFile.setContent(file.getBytes());
        wePlanFile.setSize(file.getSize());
        wePlanFile.setUploadTime(new Date());

        fileRepository.save(wePlanFile);

        Optional<Task> taskOptional = taskRepository.findById(taskId);

        if(taskOptional.get().getTaskType().equals("Simple Task")) {
                Optional<SimpleTask> personalTaskOptional = taskOptional.map(task -> (SimpleTask) task);
                personalTaskOptional.get().getTaskFiles().add(wePlanFile);
                taskRepository.save(personalTaskOptional.get());
        } else if (taskOptional.get().getTaskType().equals("Teams Task")) {
            Optional<TeamsTask> personalTaskOptional = taskOptional.map(task -> (TeamsTask) task);
            personalTaskOptional.get().getTaskFiles().add(wePlanFile);
            taskRepository.save(personalTaskOptional.get());
        }

        attributes.addAttribute("message", "Files successfully uploaded");
        return "redirect:/user-area";
    }

    @GetMapping("/download")
    public void downloadFile(@Param("id") Long id, HttpServletResponse response) throws Exception {
        Optional<WePlanFile> foundFile = fileRepository.findById(id);
        if(!foundFile.isPresent()) {
            throw new Exception("Could not find the file. Id: " + id);
        }

        WePlanFile file = foundFile.get();

        response.setContentType("application/octet-stream");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=" + file.getName();

        response.setHeader(headerKey, headerValue);
        ServletOutputStream outputStream = response.getOutputStream();
        outputStream.write(file.getContent());
        outputStream.close();
    }

    /**
     * This method uses HttpServlet response to be able to download the file that is generated, the file is
     * named with the username and the local date and time. It gets a list of the tasks of the user who is currently
     * logged in and with the SuperCsv library, it creates a CSV file with the data of the tasks and it is finally exported.
     * @param response
     * @param userInfo
     * @throws IOException
     */
    @GetMapping("/user-area/export-timetable")
    public void exportToCSV(HttpServletResponse response, @AuthenticationPrincipal WePlanUserDetails userInfo) throws IOException {

        User user = userRepository.findByEmail(userInfo.getEmail());
        response.setContentType("text/csv");
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH#mm#ss");
        String fileName = "tasks_" + user.getUsername() + "_" + dateFormat.format(new Date()) + ".csv";

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename = " + fileName;
        response.setHeader(headerKey, headerValue);

        List<Task> userTasks = TaskService.getUserTasks(taskRepository, user);;

        ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.STANDARD_PREFERENCE);

        String[] csvHeader = {"Task name", "Description", "Date", "Time", "Task type"};
        String[] nameMapping = {"name", "description", "date", "taskTime", "taskType"};

        csvWriter.writeHeader(csvHeader);

        for(Task task : userTasks)
            csvWriter.write(task, nameMapping);

        csvWriter.close();
    }
}
