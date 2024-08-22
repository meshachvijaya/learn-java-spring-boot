package com.netafel.backend.controller;

import com.netafel.backend.model.LoginRequest;
import com.netafel.backend.model.User;
import com.netafel.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody User user) {
        Optional<User> existingUser = userService.findByEmail(user.getEmail());
        if (existingUser.isPresent()) {
            return ResponseEntity.badRequest().body("Email already exist!");
        }
        userService.registerUser(user);
        return ResponseEntity.ok("User registered successfully");
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
        Optional<User> user = userService.findByEmail(loginRequest.getEmail());
        if (user.isPresent() && userService.checkPassword(user.get(), loginRequest.getPassword())) {
            return ResponseEntity.ok("Login Success");
        }
        return ResponseEntity.status(401).body("Invalid email or password!");
    }
}
