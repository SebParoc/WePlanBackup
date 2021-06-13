package com.bakaful.project2021.controllers;

import com.bakaful.project2021.domains.FriendRequest;
import com.bakaful.project2021.domains.User;
import com.bakaful.project2021.repositories.FriendRequestRepository;
import com.bakaful.project2021.repositories.UserRepository;
import com.bakaful.project2021.user_security.WePlanUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping("/profile")
    public String profile(Model model1, Model model2, @AuthenticationPrincipal WePlanUserDetails userInfo) {
        User user = userRepository.findByEmail(userInfo.getEmail());
        List<User> friendList = new ArrayList<>(user.getFriends())
                .stream()
                .sorted(Comparator.comparing(User::getUsername))
                .collect(Collectors.toList());

        model1.addAttribute("friendList", friendList);
        model2.addAttribute("imageName", user.getImage());
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
