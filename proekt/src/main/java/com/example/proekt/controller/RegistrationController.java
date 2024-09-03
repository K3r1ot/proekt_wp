package com.example.proekt.controller;

import com.example.proekt.model.User;
import com.example.proekt.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RegistrationController {

    @Autowired
    private UserService userService;

    @GetMapping("/register")
    public String showRegistrationForm() {
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(
            @RequestParam String id,
            @RequestParam String username,
            @RequestParam String password,
            Model model) {

        User registeredUser = userService.registerUser(id, username, password);
        model.addAttribute("user", registeredUser);
        return "registration-success";
    }
}
