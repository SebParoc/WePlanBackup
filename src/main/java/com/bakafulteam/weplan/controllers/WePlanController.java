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

import java.time.DayOfWeek;
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
    public String userArea(Model model1, Model model2, Model model3, Model model4,
                           @AuthenticationPrincipal WePlanUserDetails userInfo) {

        DayOfWeek start, end;
        int weekOfYear = -1;
        int i = 0;
        User user = userRepository.findByEmail(userInfo.getEmail());

        List<Task> userTasks = TaskService.getUserTasks(taskRepository, user);
        List<List<Task>> weeklyTasks = new ArrayList<>();
        List<String> weekIntervals = new ArrayList<>();

        for(Task task : userTasks) {
            if (weekOfYear != TaskService.getWeekOfYear(task.getDate())) {
                if(weekOfYear != -1)
                    i++;
                weeklyTasks.add(new ArrayList<Task>());
                weekOfYear = TaskService.getWeekOfYear(task.getDate());
                weekIntervals.add(TaskService.getWeekInterval(weekOfYear));
            }
            weeklyTasks.get(i).add(task);
        }

        List<FriendRequest> friendRequests = friendRequestRepository.findAll()
                .stream()
                .filter(friendRequest -> friendRequest.getRecipientUsername().equals(user.getUsername()))
                .collect(Collectors.toList());

        List<WePlanFile> wePlanFiles = fileRepository.findAll();

        model1.addAttribute("weeklyTasks", weeklyTasks);
        model2.addAttribute("weekIntervals", weekIntervals);
        model3.addAttribute("friendRequestList", friendRequests);
        model4.addAttribute("wePlanFiles", wePlanFiles);
        return "MainPage/UserArea";
    }

    @GetMapping("/teams-tasks")
    public String getMyTeamsTasks(@AuthenticationPrincipal WePlanUserDetails userInfo, Model model) {
        User user = userRepository.findByEmail(userInfo.getEmail());
        List<TeamsTask> teamsTasks = TaskService.getUserTasks(taskRepository, user)
                .stream()
                .filter(task -> task.getTaskType().equals("Teams Task"))
                .map(task -> (TeamsTask) task)
                .collect(Collectors.toList());
        model.addAttribute("teamsTasks", teamsTasks);
        return "MainPage/Teams";
    }
}