package com.bakaful.project2021.controllers;

import com.bakaful.project2021.domains.*;
import com.bakaful.project2021.repositories.FriendRequestRepository;
import com.bakaful.project2021.repositories.TaskRepository;
import com.bakaful.project2021.repositories.UserRepository;
import com.bakaful.project2021.repositories.WePlanFileRepository;
import com.bakaful.project2021.user_security.WePlanUserDetails;
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

        List<Task> personalTasks = taskRepository.findAll()
                .stream()
                .filter(task -> task.getTaskType().equals("Personal Task"))
                .map(task -> (PersonalTask)task)
                .filter(personalTask -> personalTask.getTaskOwner().equals(user))
                .map(personalTask -> (Task) personalTask)
                .collect(Collectors.toList());

        List<Task> teamsTasks = taskRepository.findAll()
                .stream()
                .filter(task -> task.getTaskType().equals("Teams Task"))
                .map(task -> (TeamsTask) task)
                .filter(teamsTask -> teamsTask.getCollaborators().contains(user))
                .map(teamsTask -> (Task) teamsTask)
                .collect(Collectors.toList());

        List<Task> timetableEvents = taskRepository.findAll()
                .stream()
                .filter(task -> task.getTaskType().equals("Timetable Event"))
                .map(task -> (TimeTableEvent) task)
                .filter(timeTableEvent -> timeTableEvent.getEventOwner().equals(user))
                .map(timeTableEvent -> (Task) timeTableEvent)
                .collect(Collectors.toList());

        List<Task> userTasks = new ArrayList<>();
        userTasks.addAll(personalTasks);
        userTasks.addAll(teamsTasks);
        userTasks.addAll(timetableEvents);

        userTasks.sort(Comparator.comparing(Task::getDate).thenComparing(Task::getTaskTime));

        List<WePlanFile> wePlanFiles = fileRepository.findAll();

        model1.addAttribute("userTasks", userTasks);
        model2.addAttribute("friendRequestList", friendRequests);
        model2.addAttribute("wePlanFiles", wePlanFiles);
        return "MainPage/UserArea";
    }
}