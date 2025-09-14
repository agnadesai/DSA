package com.example.vacationservice.controller;

import com.example.vacationservice.dto.JwtAuthenticationResponse;
import com.example.vacationservice.dto.LoginRequest;
import com.example.vacationservice.dto.SignupRequest;
import com.example.vacationservice.model.User;
import com.example.vacationservice.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class AuthControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private String getBaseUrl() {
        return "http://localhost:" + port + "/api/v1/auth";
    }

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
        
        // Create a test user for login tests
        User user = new User();
        user.setUsername("john");
        user.setPassword(passwordEncoder.encode("password123"));
        user.setRole(User.Role.EMPLOYEE);
        userRepository.save(user);
    }

    @Test
    void loginShouldReturnJwtToken() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("john");
        loginRequest.setPassword("password123");

        HttpEntity<LoginRequest> request = new HttpEntity<>(loginRequest);
        ResponseEntity<JwtAuthenticationResponse> response = 
            restTemplate.postForEntity(getBaseUrl() + "/login", request, JwtAuthenticationResponse.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getAccessToken()).isNotEmpty();
    }

    @Test
    void loginWithInvalidCredentialsShouldReturn401() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("john");
        loginRequest.setPassword("wrongpassword");

        HttpEntity<LoginRequest> request = new HttpEntity<>(loginRequest);
        ResponseEntity<JwtAuthenticationResponse> response = 
            restTemplate.postForEntity(getBaseUrl() + "/login", request, JwtAuthenticationResponse.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }

    @Test
    void registerShouldReturnUser() {
        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setUsername("newuser");
        signupRequest.setPassword("password123");
        signupRequest.setRole(User.Role.EMPLOYEE);

        HttpEntity<SignupRequest> request = new HttpEntity<>(signupRequest);
        ResponseEntity<User> response = 
            restTemplate.postForEntity(getBaseUrl() + "/register", request, User.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getUsername()).isEqualTo("newuser");
        assertThat(response.getBody().getRole()).isEqualTo(User.Role.EMPLOYEE);
    }

    @Test
    void registerWithExistingUsernameShouldReturn400() {
        SignupRequest firstRequest = new SignupRequest();
        firstRequest.setUsername("existinguser");
        firstRequest.setPassword("password123");
        firstRequest.setRole(User.Role.EMPLOYEE);
        restTemplate.postForEntity(getBaseUrl() + "/register", new HttpEntity<>(firstRequest), User.class);

        SignupRequest duplicateRequest = new SignupRequest();
        duplicateRequest.setUsername("existinguser");
        duplicateRequest.setPassword("password456");
        duplicateRequest.setRole(User.Role.MANAGER);

        ResponseEntity<User> response = 
            restTemplate.postForEntity(getBaseUrl() + "/register", new HttpEntity<>(duplicateRequest), User.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }
}