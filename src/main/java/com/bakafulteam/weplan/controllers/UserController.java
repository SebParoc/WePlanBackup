package com.bakafulteam.weplan.controllers;

import com.bakafulteam.weplan.Exceptions.AlreadyAddedFriendException;
import com.bakafulteam.weplan.Exceptions.NonExistentUserException;
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

import java.io.File;
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

    /**
     * Shows the profile of any user. If it is the profile of the user who is currently logged in, it will show the
     * list of friends. If the users is looking at other user's profile, the tasks that they share will be displayed.
     * Different models are sent to the view template to show specific content depending on each profile.
     * @param username
     * @param model1
     * @param model2
     * @param model3
     * @param model4
     * @param currentUserInfo
     * @return a string with the name of the view template (html)
     */
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
        model4.addAttribute("imagePath", System.getProperty("user.home") + File.separator + "WePlanFiles" + File.separator);

        return "MainPage/Profile";
    }

    /**
     * POST http request to save a friend request in the database so the recipient will be able to see it in their profile
     * whenever they log in. They can accept it or reject it. Exceptions are thrown in case of wrong input.
     * @param friendUsername
     * @param userInfo
     * @return a redirection to the http request for the user area
     */
    @PostMapping("/user-area/add-friend")
    public String addFriend(@RequestParam String friendUsername, @AuthenticationPrincipal WePlanUserDetails userInfo) {
        User user = userRepository.findByUsername(userInfo.getUsername());

        FriendRequest newRequest = new FriendRequest();
        newRequest.setSenderUsername(userInfo.getUsername());
        newRequest.setRecipientUsername(friendUsername);

        User user2 = userRepository.findByUsername(friendUsername);
        if(user.getFriends().contains(user2)){
            throw new AlreadyAddedFriendException(friendUsername);
        }
        if(user2==null){
            throw new NonExistentUserException(friendUsername);
        }
        friendRequestRepository.save(newRequest);

        return "redirect:/user-area";
    }


    /**
     * POST request to manage a friend request. If accepted, the users will be added in each one's
     * friend list. It will delete the request from the database so it will not be displayed anymore
     * @param senderUsername
     * @param accepted
     * @param userInfo
     * @return a redirection to the http request for the user area
     */
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

    /**
     * GET http request to remove a friend. It is removed from both list of friends
     * @param friendUsername
     * @param userInfo
     * @return a redirection to the http request for the profile
     */
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
