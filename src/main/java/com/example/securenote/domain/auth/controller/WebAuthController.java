package com.example.securenote.domain.auth.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebAuthController {

    @GetMapping("/web/auth/login")
    public String loginView(){
        return "login";
    }

    @GetMapping("/web/home")
    public String homeView(){
        return "home";
    }
}
