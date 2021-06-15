package com.bakaful.project2021.controllers;

import com.bakaful.project2021.domains.Task;
import com.bakaful.project2021.domains.Timer;
import com.bakaful.project2021.repositories.TimerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class TimerController {
    
    @Autowired
    TimerRepository timerRepository;

    @GetMapping("/timer")
    public String init(Model model) {
        List<Timer> timerList = timerRepository.findAll();
        model.addAttribute("timerList", timerList);
        return "Extras/Study-Mode";
    }

    @GetMapping("/dashboard")
    public String goBack(){
        return "redirect:/user-area";
    }

    @GetMapping("/create-timer")
    public String createTimer(Model model){
        model.addAttribute("timer", new Timer());
        return "Extras/Create-Timer";
    }

    @PostMapping("/create-timer")
    public String postSubmit(Timer timer){
        timerRepository.save(timer);
        return "redirect:/timer";
    }



        @RequestMapping("pomodoro")
         public String getContent(){
            return "Extras/Pomodoro :: pomodoro";
        }
    


}
