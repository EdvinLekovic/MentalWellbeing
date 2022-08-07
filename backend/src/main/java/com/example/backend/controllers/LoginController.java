package com.example.backend.controllers;

import com.example.backend.config.util.JwtUtil;
import com.example.backend.models.User;
import com.example.backend.models.requests.user_requests.AuthRequest;
import com.example.backend.models.requests.user_requests.EditUserRequest;
import com.example.backend.models.requests.user_requests.TokenRequest;
import com.example.backend.models.requests.user_requests.UserRequest;
import com.example.backend.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/users")
@CrossOrigin("http://localhost:4200")
public class LoginController {
    private final UserService authService;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    public LoginController(UserService authService,
                           JwtUtil jwtUtil,
                           AuthenticationManager authenticationManager) {
        this.authService = authService;
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
    }

    @GetMapping("/get-user-by-id/{userName}")
    public ResponseEntity<User> getUserById(@PathVariable String userName) {
        return authService.findUserByUsername(userName)
                .map(user -> ResponseEntity.ok().body(user))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @PostMapping("/user-expiration")
    public boolean userValidity(@RequestBody TokenRequest tokenRequest) {
        return !jwtUtil.isTokenExpired(tokenRequest.getToken());
    }

    @PostMapping("/get-user-by-token")
    public ResponseEntity<User> getUserByToken(@RequestBody TokenRequest tokenRequest) {
        String username = jwtUtil.extractUsername(tokenRequest.getToken());
        return authService.findUserByUsername(username).map(user -> ResponseEntity.ok().body(user))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody UserRequest userRequest) {
        return authService.register(userRequest).map(user -> ResponseEntity.ok().body(user))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @PostMapping("/edit-user")
    public Optional<User> editUserInformation(@RequestBody EditUserRequest editUserRequest) {
        return authService.editUserInformation(editUserRequest);
    }

    @PostMapping("/authenticate")
    public String authenticate(@RequestBody AuthRequest authRequest) throws Exception {
        System.out.println(authRequest);
        System.out.println(authRequest);
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authRequest.getUsername(),
                            authRequest.getPassword()));
        } catch (Exception ex) {
            throw new Exception("invalid username/password");
        }
        return jwtUtil.generateToken(authRequest.getUsername());
    }
}
