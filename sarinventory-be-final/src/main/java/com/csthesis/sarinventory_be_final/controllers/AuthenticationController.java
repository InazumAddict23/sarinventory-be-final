package com.csthesis.sarinventory_be_final.controllers;

import com.csthesis.sarinventory_be_final.Dto.LoginDTO;
import com.csthesis.sarinventory_be_final.Dto.RegisterDTO;
import com.csthesis.sarinventory_be_final.entities.ErrorResponse;
import com.csthesis.sarinventory_be_final.entities.User;
import com.csthesis.sarinventory_be_final.services.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping ("/api/auth")
@CrossOrigin("*")
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping ("/register")
    public ResponseEntity<?> register(@RequestBody RegisterDTO body) {
        try {
            User user = authenticationService.registerUser(body.getUsername(), body.getPhoneNo(), body.getFirstName(), body.getLastName(), body.getStoreName(), body.getPassword());
            return ResponseEntity.ok(user);
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.badRequest()
                    .body(new ErrorResponse(400, "User already exists, please enter new credentials"));
        }
     }

    @PostMapping("/login")
    public LoginDTO login(@RequestBody RegisterDTO body) {
        return authenticationService.login(body.getUsername(), body.getPassword());
    }

}
