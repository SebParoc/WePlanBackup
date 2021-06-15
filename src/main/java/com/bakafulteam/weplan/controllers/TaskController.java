package com.bakafulteam.weplan.controllers;

import com.bakafulteam.weplan.domains.*;
import com.bakafulteam.weplan.repositories.UserRepository;
import com.bakafulteam.weplan.repositories.TaskRepository;
import com.bakafulteam.weplan.user_security.WePlanUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

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
            case "Personal Task":
                model1.addAttribute("task", new PersonalTask());
                model2.addAttribute("taskType", "personal-task");
                break;
            case "Teams Task":
                model1.addAttribute("task", new TeamsTask());
                model2.addAttribute("taskType", "teams-task");
                break;
            case "Timetable Event":
                model1.addAttribute("task", new TimeTableEvent());
                model2.addAttribute("taskType", "timetable-event");
                System.out.println("YAS");
                break;
            default:
                break;
        }
        return "TaskManager/CreateTask";
    }

    @PostMapping("/create-personal-task")
    public String CreatePersonalTask(PersonalTask task, @AuthenticationPrincipal WePlanUserDetails userInfo) {
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

    @PostMapping("/create-timetable-event")
    public String CreateTimetableEvent(TimeTableEvent task, @AuthenticationPrincipal WePlanUserDetails userInfo) {
        user = userRepository.findByEmail(userInfo.getEmail());
        task.setEventOwner(user);
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
}
