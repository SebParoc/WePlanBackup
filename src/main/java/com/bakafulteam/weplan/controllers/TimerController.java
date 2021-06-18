package com.bakafulteam.weplan.controllers;


import com.bakafulteam.weplan.domains.Timer;
import com.bakafulteam.weplan.domains.User;
import com.bakafulteam.weplan.repositories.TimerRepository;
import com.bakafulteam.weplan.repositories.UserRepository;
import com.bakafulteam.weplan.user_security.WePlanUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class TimerController {
    @Autowired
    UserRepository userRepository;

    @Autowired
    TimerRepository timerRepository;

    @GetMapping("/timer")
    public String init(Model model, Model model2, @RequestParam(required = false) Long timerId,
                       @AuthenticationPrincipal WePlanUserDetails userInfo) {
        User user = userRepository.findByEmail(userInfo.getEmail());
        List<Timer> timerList = timerRepository.findAll()
                .stream()
                .filter(timer -> timer.getTimerOwner().equals(user))
                .collect(Collectors.toList());
        model.addAttribute("timerList", timerList);
        if(timerId != null) {
            Timer timer = timerRepository.getOne(timerId);
            model2.addAttribute("displayTimer", timer);
        }
        return "Extras/Study-Mode";
    }

    @GetMapping("/dashboard")
    public String goBack(){
        return "redirect:/user-area";
    }

    @GetMapping("/create-timer")
    public String createTimer(Model model) {
        model.addAttribute("timer", new Timer());
        return "Extras/Create-Timer";
    }

    @PostMapping("/create-timer")
    public String postSubmit(Timer timer, @AuthenticationPrincipal WePlanUserDetails userInfo){
        User user = userRepository.findByEmail(userInfo.getEmail());
        timer.setTimerOwner(user);
        timerRepository.save(timer);
        return "redirect:/timer";
    }

    @GetMapping("/delete-timer")
    public String deleteTimer(@RequestParam Long timerId) {
        timerRepository.deleteById(timerId);
        return "redirect:/timer";
    }
}
