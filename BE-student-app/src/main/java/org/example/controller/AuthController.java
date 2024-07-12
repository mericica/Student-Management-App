package org.example.controller;

import org.example.model.LoginRequest;
import org.example.model.LoginResponse;
import org.example.model.SignUpRequest;
import org.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userDbService;

    public AuthController(UserService userDbService) {
        this.userDbService = userDbService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        LoginResponse loginResponse = userDbService.login(loginRequest);
        return ResponseEntity.ok(loginResponse);
    }

    @PostMapping("/signup")
    public ResponseEntity<String> signUp(@RequestBody SignUpRequest signUpRequest) throws Exception {
        userDbService.signUp(signUpRequest);
        return ResponseEntity.ok("User successfully registered");
    }
}