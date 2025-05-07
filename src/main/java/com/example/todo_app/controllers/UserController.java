package com.example.todo_app.controllers;

import com.example.todo_app.models.User;
import com.example.todo_app.models.UserDto;
import com.example.todo_app.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import java.security.Principal;

@Controller
public class UserController {

    @Autowired
    private UserDetailsService userDetailsService;



    /*
    Even though you're referencing the interface UserService, Spring is injecting an instance
    of the implementation class UserServiceImpl.
    Spring sees:
        “OK, I need to inject a bean that implements UserService.”
    It finds UserServiceImpl (because it's annotated with @Service), and injects an instance of it.
    private final UserService userService;
    so UserService is not really injected here because is not a bean is a simple interface.*/

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    /*Spring automatically provides the Principal based on who is logged in. Internally, Spring builds it
    from the Authentication object in the SecurityContext.*/
    @GetMapping("/home")
    public String home(Model model, Principal principal) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
        model.addAttribute("userdetail", userDetails);
        return "home";
    }

    @GetMapping("/login")
    public String login(Model model, UserDto userDto) {
        model.addAttribute("user", userDto);
        return "login";
    }

    @GetMapping("/register")
    public String register(Model model, UserDto userDto) {
        model.addAttribute("user", userDto);
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") UserDto userDto, Model model) {
        User user = userService.findByUsername(userDto.getUsername());
        if (user != null) {
            model.addAttribute("Userexist", user);
            return "register";
        }
        userService.save(userDto);
        return "redirect:/register?success";
    }
}
