package com.cloudeasy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class ApplicationController {


    @RequestMapping("/")
    public String serveIndexPage() {
        return "index";
    }

    @RequestMapping("/login")
    public String serveLoginPage() {
        return "pages/sign-in";
    }

    @RequestMapping("/register")
    public String serveRegistrationPage() {
        return "pages/sign-up";
    }

}
