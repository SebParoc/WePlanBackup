package com.bakaful.project2021.controllers;

import com.bakaful.project2021.domains.User;
import com.bakaful.project2021.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AppController {

    @Autowired
    private UserRepository userRepo;

    @GetMapping("")
    public String viewHomePage() {
        return "MainPage/MainPage";
    }

    @GetMapping("/register")
    public String register_login(Model model) {
        model.addAttribute("user", new User());
        return "Register/Register_form";
    }

    @PostMapping("/register-user")
    public String registerUser(User user) {
        userRepo.save(user);
        return "Register/Register_done";
    }
}