package com.example.vacationservice.controller;

import com.example.vacationservice.dto.VacationRequestDto;
import com.example.vacationservice.model.VacationRequest;
import com.example.vacationservice.service.VacationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private VacationService vacationService;

    @GetMapping("/requests")
    public ResponseEntity<List<VacationRequest>> getMyRequests(
            @AuthenticationPrincipal UserDetails userDetails) {
        List<VacationRequest> requests = vacationService.getUserRequests(userDetails.getUsername());
        return ResponseEntity.ok(requests);
    }

    @GetMapping("/remaining-days")
    public ResponseEntity<Integer> getRemainingDays(@AuthenticationPrincipal UserDetails userDetails) {
        int remainingDays = vacationService.getRemainingVacationDays(userDetails.getUsername());
        return ResponseEntity.ok(remainingDays);
    }

    @PostMapping("/request")
    public ResponseEntity<VacationRequest> createRequest(
            @AuthenticationPrincipal UserDetails userDetails,
            @Valid @RequestBody VacationRequestDto requestDto) {
        if (!requestDto.getVacationStartDate().isAfter(LocalDateTime.now())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Vacation start date must be in the future");
        }
        VacationRequest request = vacationService.createRequest(userDetails.getUsername(), requestDto);
        return ResponseEntity.ok(request);
    }
}
