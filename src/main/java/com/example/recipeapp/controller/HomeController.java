package com.example.recipeapp.controller;

import com.example.recipeapp.dto.UserDto;
import com.example.recipeapp.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class HomeController {

    @Autowired
    UserService userService;



    @GetMapping("/access-denied")
    public String accessDeniedPage() {
        return "access-denied"; // Points to access-denied.html
    }


    @GetMapping("/login")
    public String loadLoginPage() {
        return "login";
    }

    @GetMapping("/signup")
    public String loadSignUpPage() {
        return "signup";
    }


    @PostMapping("/signup")
    public String registerUser(@ModelAttribute("userDto") UserDto userDto, Model model) {
        String email = userDto.getEmail();
        if (userService.isEmailAlreadyRegister(email)) {
            model.addAttribute("error", "Email already registered");
            return "signup";
        }
        userService.registerUser(userDto);
        return "redirect:/login";
    }


}
