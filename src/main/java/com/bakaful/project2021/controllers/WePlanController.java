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
import org.springframework.web.servlet.ModelAndView;

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
    public String userArea(Model model, @AuthenticationPrincipal WePlanUserDetails user) {
        List<Task> taskList = taskRepository.findAll();
        model.addAttribute("listTasks", taskList);
        return "MainPage/MainPage";
    }

    @PostMapping("/user-area")
    public String userAreaFunctions(Model model, @RequestParam(value = "accepted", required = false) String accepted,
                                    @AuthenticationPrincipal WePlanUserDetails user)
    {
        User currentUser = userRepository.findByEmail(user.getEmail());

        /*FriendRequestWrapper friendRequestForm = new FriendRequestWrapper();*/
        List<FriendRequest> friendRequestList = friendRequestRepository.findAll()
                .stream()
                .filter(friendRequest -> friendRequest.getRecipient() == currentUser)
                .collect(Collectors.toList());

        if(friendRequestRepository.count() != 0 && accepted != null) {

            User otherUser = friendRequestList.get(0).getSender();
            if(accepted.equals("Accept")) {
                currentUser.getFriendList().add(friendRequestList.get(0).getSender());
                otherUser.getFriendList().add(currentUser);
            }
            friendRequestRepository.delete(friendRequestList.get(0));
        }

        //friendRequestForm.addAllRequests(friendRequestList);
        model.addAttribute("friendRequestList", friendRequestList);
        return "MainPage/MainPage";
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
        List<Task> taskList = taskRepository.findAll();
        model.addAttribute("listTasks", taskList);
        return "TaskManager/Task_list";
    }

    @PostMapping("/user-area/add-friend")
    public String addFriend(@RequestParam String friendUsername, @AuthenticationPrincipal WePlanUserDetails user) {

        FriendRequest newRequest = new FriendRequest();
        newRequest.setSender(userRepository.findByEmail(user.getEmail()));
        newRequest.setRecipient(userRepository.findByUsername(friendUsername));
        friendRequestRepository.save(newRequest);

        return "Mainpage/Mainpage";
    }

    @PostMapping("/add-collaborator")
    public String addCollaborator(@RequestParam String collaboratorUsername, @RequestParam String taskId){
        Task task = taskRepository.getOne(Long.parseLong(taskId));
        User collab = userRepository.findByUsername(collaboratorUsername);
        System.out.println(task);
        System.out.println(collab.getUsername());
        System.out.println(task.getTaskOwners());
        task.getTaskOwners().add(collab);
        return "Mainpage/Mainpage";
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

    @GetMapping("/timer")
    public String init() {
       /*model.addAttribute("taskForm", new TaskForm());*/
        return "Extras/Study-Mode";
    }

    @GetMapping("/dashboard")
     public String goBack(){
        return "MainPage/MainPage";
    }


    /*@PostMapping("/timer")
    @ResponseBody
    public String taskRegister(Model model, @RequestBody TaskForm taskForm) {

        try {

            Task regTask = registerService.registerTask(taskForm);

            return regTask.getId().toString();

        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }*/


   /* @PostMapping("/stop")
    @ResponseBody
    public String taskEnd(Model model, @RequestBody TaskForm taskForm) {

        try {
            cancelService.endTask(taskForm);
        } catch(RuntimeException e) {
            e.printStackTrace();
            return "error";
        }

        return "task canceling success";
    }*/

}