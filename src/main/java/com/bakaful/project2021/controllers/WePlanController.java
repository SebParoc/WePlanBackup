package com.bakaful.project2021.controllers;

import com.bakaful.project2021.domains.FriendRequest;
import com.bakaful.project2021.domains.Task;
import com.bakaful.project2021.domains.User;
import com.bakaful.project2021.repositories.FriendRequestRepository;
import com.bakaful.project2021.repositories.TaskRepository;
import com.bakaful.project2021.repositories.UserRepository;
import com.bakaful.project2021.user_security.WePlanUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
    public String userArea(Model model1, Model model2, Model model3, @AuthenticationPrincipal WePlanUserDetails user) {
        User currentUser = userRepository.findByUsername(user.getUsername());

        List<FriendRequest> friendRequestList = friendRequestRepository.findAll()
                .stream()
                .filter(friendRequest -> friendRequest.getRecipient() == currentUser)
                .collect(Collectors.toList());

        List<Task> taskList = taskRepository.findAll()
                .stream()
                .filter(task -> task.getTaskOwners().contains(currentUser))
                .collect(Collectors.toList());

        List<Task> personalTasks = taskList
                .stream()
                .filter(task -> task.getShareable().equals("Personal task"))
                .collect(Collectors.toList());
        List<Task> teamsTasks = taskList
                .stream()
                .filter(task -> task.getShareable().equals("Teams task"))
                .collect(Collectors.toList());

        model1.addAttribute("personalTasks", personalTasks);
        model2.addAttribute("teamsTasks", teamsTasks);
        model3.addAttribute("friendRequestList", friendRequestList);
        return "MainPage/MainPage";
    }
    
    @GetMapping("/profile")
    public String profile(Model model, @AuthenticationPrincipal WePlanUserDetails userInfo) {
        User user = userRepository.findByEmail(userInfo.getEmail());
        model.addAttribute("friendList", user.getFriendList());
        return "MainPage/Profile";
    }

    @GetMapping("/profile/remove-friend")
    public String deleteFriend(@RequestParam String friendUsername,
                               @AuthenticationPrincipal WePlanUserDetails userInfo) {
        User user = userRepository.findByUsername(userInfo.getUsername());
        User exFriend = userRepository.findByUsername(friendUsername);

        user.getFriendList().remove(exFriend);
        exFriend.getFriendList().remove(user);
        userRepository.save(user);

        return "redirect:/profile";
    }

    @PostMapping("/user-area/manage-request")
    public String AcceptOrDeclineRequest(@RequestParam Long senderId, @RequestParam(value = "accepted", required = false) String accepted,
                                         @AuthenticationPrincipal WePlanUserDetails userInfo) {
        User recipient = userRepository.findByUsername(userInfo.getUsername());
        User sender = userRepository.getOne(senderId);
        recipient.getFriendList().add(sender);
        sender.getFriendList().add(recipient);
        friendRequestRepository.delete(friendRequestRepository.findBySender(sender));
        return "redirect:/user-area";
    }

    @GetMapping("/user-area/remove-task")
    public String removeTask(@RequestParam Long taskId) {
        taskRepository.deleteById(taskId);
        return "redirect:/user-area";
    }

    @GetMapping("/create-task")
    public String CreateTask(Model model) {
        model.addAttribute("task", new Task());
        return "TaskManager/CreateTask";
    }

    @PostMapping("/created-task")
    public String CreatedTask(Task task, @AuthenticationPrincipal WePlanUserDetails userInfo) {
        User user = userRepository.findByEmail(userInfo.getEmail());
        task.getTaskOwners().add(user);
        taskRepository.save(task);
        return "redirect:/user-area";
    }

    @PostMapping("/user-area/add-friend")
    public String addFriend(@RequestParam String friendUsername, @AuthenticationPrincipal WePlanUserDetails user) {

        FriendRequest newRequest = new FriendRequest();
        newRequest.setSender(userRepository.findByEmail(user.getEmail()));
        newRequest.setRecipient(userRepository.findByUsername(friendUsername));
        friendRequestRepository.save(newRequest);

        return "redirect:/user-area";
    }

    @PostMapping("/user-area/add-collaborator")
    public String addCollaborator(@RequestParam String collaboratorUsername, @RequestParam Long taskId){
        Task task = taskRepository.getOne(taskId);
        User collab = userRepository.findByUsername(collaboratorUsername);
        task.getTaskOwners().add(collab);
        taskRepository.save(task);
        return "redirect:/user-area";
    }


    @GetMapping("/login")
    public String login(Model model, String error, String logout) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication==null || authentication instanceof AnonymousAuthenticationToken) {
            return "login";
        }
            /* if (error != null)
            model.addAttribute("error", "Your username and password is invalid.");

        if (logout != null){
            model.addAttribute("message", "You have been logged out successfully.");
        return "LandingPage";}*/


        return "redirect:/";
    }

}