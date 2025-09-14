package com.example.vacationservice.dto;

import com.example.vacationservice.model.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SignupRequest {
    @NotBlank
    private String username;
    @NotBlank
    private String password;
    @NotNull
    private User.Role role;
}
