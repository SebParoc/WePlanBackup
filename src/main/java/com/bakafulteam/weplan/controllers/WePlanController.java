package com.bakafulteam.weplan.controllers;

import com.bakafulteam.weplan.domains.*;
import com.bakafulteam.weplan.repositories.FriendRequestRepository;
import com.bakafulteam.weplan.repositories.TaskRepository;
import com.bakafulteam.weplan.repositories.UserRepository;
import com.bakafulteam.weplan.repositories.WePlanFileRepository;

import com.bakafulteam.weplan.services.TaskService;
import com.bakafulteam.weplan.user_security.WePlanUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@SuppressWarnings("ALL")
@Controller
public class WePlanController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private FriendRequestRepository friendRequestRepository;

    @Autowired
    private WePlanFileRepository fileRepository;

    @GetMapping("")
    public String viewHomePage() {
        return "MainPage/LandingPage";
    }

    @GetMapping("/user-area")
    public String userArea(Model model1, Model model2, Model model3,
                           @AuthenticationPrincipal WePlanUserDetails userInfo) {
        User user = userRepository.findByEmail(userInfo.getEmail());

        List<FriendRequest> friendRequests = friendRequestRepository.findAll()
                .stream()
                .filter(friendRequest -> friendRequest.getRecipientUsername().equals(user.getUsername()))
                .collect(Collectors.toList());

        List<Task> userTasks = TaskService.getUserTasks(taskRepository, user);

        List<WePlanFile> wePlanFiles = fileRepository.findAll();

        model1.addAttribute("userTasks", userTasks);
        model2.addAttribute("friendRequestList", friendRequests);
        model3.addAttribute("wePlanFiles", wePlanFiles);
        return "MainPage/UserArea";
    }
}