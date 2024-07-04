package com.csthesis.sarinventory_be_final.controllers;

import com.csthesis.sarinventory_be_final.Dto.LoginDTO;
import com.csthesis.sarinventory_be_final.Dto.RegisterDTO;
import com.csthesis.sarinventory_be_final.entities.User;
import com.csthesis.sarinventory_be_final.services.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping ("/api/auth")
@CrossOrigin("*")
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping ("/register")
    public User register(@RequestBody RegisterDTO body) {
        return authenticationService.registerUser(body.getUsername(), body.getPhoneNo(), body.getFirstName(), body.getLastName(), body.getStoreName(), body.getPassword());
    }

    @PostMapping("/login")
    public LoginDTO login(@RequestBody RegisterDTO body) {
        return authenticationService.login(body.getUsername(), body.getPassword());
    }

}
