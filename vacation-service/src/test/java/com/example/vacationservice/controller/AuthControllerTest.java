package com.example.vacationservice.controller;

import com.example.vacationservice.dto.JwtAuthenticationResponse;
import com.example.vacationservice.dto.LoginRequest;
import com.example.vacationservice.dto.SignupRequest;
import com.example.vacationservice.model.User;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class AuthControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void loginShouldReturnJwtToken() {
        String url = "http://localhost:" + port + "/auth/login";
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("john");
        loginRequest.setPassword("password123");

        HttpEntity<LoginRequest> request = new HttpEntity<>(loginRequest);

        ResponseEntity<JwtAuthenticationResponse> response = restTemplate.postForEntity(url, request, JwtAuthenticationResponse.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getAccessToken()).isNotEmpty();
    }

    @Test
    void registerShouldReturnUser() {
        String url = "http://localhost:" + port + "/auth/register";
        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setUsername("newuser");
        signupRequest.setPassword("password123");
        signupRequest.setRole(User.Role.EMPLOYEE);

        HttpEntity<SignupRequest> request = new HttpEntity<>(signupRequest);

        ResponseEntity<User> response = restTemplate.postForEntity(url, request, User.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getUsername()).isEqualTo("newuser");
        assertThat(response.getBody().getRole()).isEqualTo(User.Role.EMPLOYEE);
    }
}