package com.csthesis.sarinventory_be_final.controllers;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping ("/user")
@CrossOrigin("*")
public class UserController {

    @GetMapping("/login")
    public String login() {
        return "User logged in";
    }
}
