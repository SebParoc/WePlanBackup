package com.bakafulteam.weplan.controllers;

import com.bakafulteam.weplan.domains.User;
import com.bakafulteam.weplan.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
public class LoginController {

    @InitBinder
    public void initBinder(WebDataBinder binder){
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true) );
    }

    @Autowired
    UserRepository userRepository;

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


    @GetMapping("/login")
    public String login(Model model, String error, String logout) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication==null || authentication instanceof AnonymousAuthenticationToken) {
            return "login";
        }

        return "redirect:/";
    }
}
