package com.example.vacationservice.controller;

import com.example.vacationservice.dto.JwtAuthenticationResponse;
import com.example.vacationservice.dto.LoginRequest;
import com.example.vacationservice.dto.SignupRequest;
import com.example.vacationservice.model.User;
import com.example.vacationservice.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@Tag(name = "Authentication", description = "Authentication management API")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Operation(summary = "Login user", description = "Authenticate a user and return a JWT token")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully authenticated"),
        @ApiResponse(responseCode = "401", description = "Invalid credentials")
    })
    @PostMapping("/login")
    public ResponseEntity<JwtAuthenticationResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(authService.authenticateUser(loginRequest));
    }

    @Operation(summary = "Register user", description = "Register a new user with EMPLOYEE or MANAGER role")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Successfully registered"),
        @ApiResponse(responseCode = "400", description = "Username already taken")
    })
    @PostMapping("/register")
    public ResponseEntity<User> register(@Valid @RequestBody SignupRequest signupRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.registerUser(signupRequest));
    }
}
