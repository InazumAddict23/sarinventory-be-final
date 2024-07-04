package com.csthesis.sarinventory_be_final.controllers;

import com.csthesis.sarinventory_be_final.entities.User;
import com.csthesis.sarinventory_be_final.repositories.UserRepository;
import com.csthesis.sarinventory_be_final.services.ItemService;
import com.csthesis.sarinventory_be_final.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping ("/user")
@CrossOrigin("*")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/")
    public String helloUser() {
        return "User logged in";
    }

    @GetMapping("/userinfo")
    public User helloUsername(Authentication auth) {
        return userService.loadUserByUsername(auth.getName());
    }

    @GetMapping("/store")
    public String helloStore(Authentication auth) {
        return ((User) auth.getPrincipal()).getStoreName();
    }

    @GetMapping("/username2")
    public String helloUsername2(Authentication auth) {
        return auth.getDetails().toString();
    }

}
