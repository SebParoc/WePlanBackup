package com.bakafulteam.weplan.controllers;

import com.bakafulteam.weplan.domains.FriendRequest;
import com.bakafulteam.weplan.domains.TeamsTask;
import com.bakafulteam.weplan.domains.User;
import com.bakafulteam.weplan.repositories.FriendRequestRepository;
import com.bakafulteam.weplan.repositories.TaskRepository;
import com.bakafulteam.weplan.repositories.UserRepository;
import com.bakafulteam.weplan.services.TaskService;
import com.bakafulteam.weplan.user_security.WePlanUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class UserController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    FriendRequestRepository friendRequestRepository;

    @Autowired
    TaskRepository taskRepository;

    @GetMapping("/profile")
    public String profile(@Param("username") String username, Model model1, Model model2, Model model3, Model model4,
                          @AuthenticationPrincipal WePlanUserDetails currentUserInfo) {

        User userProfile = userRepository.findByUsername(username);
        User currentUser = userRepository.findByUsername(currentUserInfo.getUsername());

        List<User> friendList = new ArrayList<>(currentUser.getFriends())
                .stream()
                .sorted(Comparator.comparing(User::getUsername))
                .collect(Collectors.toList());

        List<TeamsTask> teamsTasks = TaskService.getUserTasks(taskRepository, currentUser)
                .stream()
                .filter(task -> task.getTaskType().equals("Teams Task"))
                .map(task -> (TeamsTask) task)
                .filter(teamsTask -> teamsTask.getCollaborators().contains(userProfile))
                .collect(Collectors.toList());

        model1.addAttribute("friendList", friendList);
        model2.addAttribute("teamsTaskList", teamsTasks);
        model3.addAttribute("userProfile", userProfile);

        return "MainPage/Profile";
    }

    @PostMapping("/user-area/add-friend")
    public String addFriend(@RequestParam String friendUsername, @AuthenticationPrincipal WePlanUserDetails user) {

        FriendRequest newRequest = new FriendRequest();
        newRequest.setSenderUsername(user.getUsername());
        newRequest.setRecipientUsername(friendUsername);
        friendRequestRepository.save(newRequest);

        return "redirect:/user-area";
    }

    @PostMapping("/user-area/manage-request")
    public String AcceptOrDeclineRequest(@RequestParam String senderUsername, @RequestParam(value = "accepted", required = false) String accepted,
                                         @AuthenticationPrincipal WePlanUserDetails userInfo) {
        User recipient = userRepository.findByUsername(userInfo.getUsername());
        User sender = userRepository.findByUsername(senderUsername);
        recipient.getFriends().add(sender);
        sender.getFriends().add(recipient);
        friendRequestRepository.delete(friendRequestRepository.findByUsernames(senderUsername, userInfo.getUsername()));
        return "redirect:/user-area";
    }

    @GetMapping("/profile/remove-friend")
    public String deleteFriend(@RequestParam String friendUsername,
                               @AuthenticationPrincipal WePlanUserDetails userInfo) {
        User user = userRepository.findByUsername(userInfo.getUsername());
        User exFriend = userRepository.findByUsername(friendUsername);

        user.getFriends().remove(exFriend);
        exFriend.getFriends().remove(user);
        userRepository.save(user);

        return "redirect:/profile";
    }
}
