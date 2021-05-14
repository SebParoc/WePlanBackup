package com.bakaful.project2021.controllers;

import com.bakaful.project2021.domains.Task;
import com.bakaful.project2021.domains.User;
import com.bakaful.project2021.repositories.TaskRepository;
import com.bakaful.project2021.repositories.UserRepository;
import com.bakaful.project2021.user_security.WePlanUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.cdi.Eager;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Collectors;

@SuppressWarnings("ALL")
@Controller
public class    WePlanController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TaskRepository taskRepository;

    @GetMapping("")
    public String viewHomePage() {
        return "MainPage/LandingPage";
    }

    @GetMapping("/register")
    public String register_login(Model model) {
        model.addAttribute("user", new User());
        return "Register_Login/Register_form";
    }

    @PostMapping("/register-user")
    public String registerUser(User user) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodedPassword = encoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        userRepository.save(user);
        return "Register_Login/Register_done";
    }

    @GetMapping("/user-area")
    public String toHome() {
        return "MainPage/MainPage";
    }

    @GetMapping("/create-task")
    public String CreateTask(Model model) {
        model.addAttribute("task", new Task());
        return "TaskManager/CreateTask";
    }

    @PostMapping("/created-task")
    public String CreatedTask(Task task, @AuthenticationPrincipal WePlanUserDetails user) {
        task.setTaskOwner(user.getUsername());
        taskRepository.save(task);
        return "MainPage/MainPage";
    }

    @GetMapping("/user-list")
    public String viewUsersList(Model model) {
        List<User> userList = userRepository.findAll();
        model.addAttribute("listUsers", userList);
        return "Register_Login/User_list";
    }

    @GetMapping("/task-list")
    public String viewTasksList(Model model, @AuthenticationPrincipal WePlanUserDetails user) {
        User friendlyUser = userRepository.findByUsername(user.getUsername());
        List<Task> taskList = taskRepository.findAll()
                .stream()
                .filter(task -> friendlyUser.getFriendList().contains(task.getTaskOwner())|| task.getTaskOwner().equals(friendlyUser.getUsername()))
                .collect(Collectors.toList());

        model.addAttribute("listTasks", taskList);
        return "TaskManager/Task_list";
    }

    @PostMapping("/user-area")
    public String addFriend(@RequestParam String friendUsername, @AuthenticationPrincipal WePlanUserDetails user) {
        User updateUser = userRepository.findByEmail(user.getEmail());
        updateUser.getFriendList().add(friendUsername);
        userRepository.save(updateUser);
        return "Mainpage/Mainpage";
    }
}