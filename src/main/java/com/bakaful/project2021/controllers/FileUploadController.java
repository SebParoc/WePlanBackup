package com.bakaful.project2021.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class FileUploadController {

    @RequestMapping("/profile/upload-profile-pic")
    public String uploadImage(Model model, @RequestParam("image") MultipartFile file) {
        return "MainPage/Profile";
    }

}
