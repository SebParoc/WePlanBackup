package com.bakafulteam.weplan.controllers;

import com.bakafulteam.weplan.domains.*;
import com.bakafulteam.weplan.repositories.TaskRepository;
import com.bakafulteam.weplan.repositories.UserRepository;
import com.bakafulteam.weplan.repositories.WePlanFileRepository;
import com.bakafulteam.weplan.user_security.WePlanUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.Optional;

@Controller
public class FileUploadController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    WePlanFileRepository fileRepository;

    @Autowired
    TaskRepository taskRepository;

    @PostMapping("/profile/upload-profile-pic")
    public String uploadImage(@RequestParam("image") MultipartFile image, @AuthenticationPrincipal WePlanUserDetails userInfo) throws IOException {

        String newImageName = userInfo.getUsername() +
                image.getOriginalFilename().substring(image.getOriginalFilename().indexOf("."));
        User user = userRepository.findByUsername(userInfo.getUsername());
        user.setImage(newImageName);
        userRepository.save(user);
        byte[] imageBytes = image.getBytes();

        String profilePicDirectory = System.getProperty("user.dir") +
                File.separator + "src" +
                File.separator + "main" +
                File.separator + "resources" +
                File.separator + "static" +
                File.separator + "profile_pictures" +
                File.separator + newImageName;
        Path path = Paths.get(profilePicDirectory);
        Files.write(path, imageBytes);
        return "redirect:/profile";
    }

    @PostMapping("/user-area/upload-file")
    public String uploadImage(@RequestParam("file") MultipartFile file,
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

        if(taskOptional.get().getTaskType().equals("Personal Task")) {
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
}
