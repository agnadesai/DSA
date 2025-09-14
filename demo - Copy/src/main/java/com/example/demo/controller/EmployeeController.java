package com.example.demo.controller;

import com.example.demo.dto.VacationRequestDTO;
import com.example.demo.mapper.VacationRequestMapper;
import com.example.demo.model.RequestStatus;
import com.example.demo.model.User;
import com.example.demo.model.VacationRequest;
import com.example.demo.service.VacationService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/employee")
public class EmployeeController {

    private final VacationService vacationService;
    private final VacationRequestMapper vacationRequestMapper;

    public EmployeeController(VacationService vacationService, VacationRequestMapper vacationRequestMapper) {
        this.vacationService = vacationService;
        this.vacationRequestMapper = vacationRequestMapper;
    }

    @GetMapping("/requests")
    public List<VacationRequestDTO> getMyRequests(@AuthenticationPrincipal User user) {
        return vacationService.getEmployeeRequests(user).stream()
                .map(vacationRequestMapper::toDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/requests/filter")
    public List<VacationRequestDTO> getMyRequestsByStatus(
            @AuthenticationPrincipal User user,
            @RequestParam RequestStatus status) {
        return vacationService.getEmployeeRequestsByStatus(user, status).stream()
                .map(vacationRequestMapper::toDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/vacation-days")
    public ResponseEntity<Integer> getRemainingVacationDays(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(vacationService.getRemainingVacationDays(user));
    }

    @PostMapping("/requests")
    public ResponseEntity<VacationRequestDTO> createRequest(
            @AuthenticationPrincipal User user,
            @Valid @RequestBody VacationRequest request) {
        try {
            VacationRequest savedRequest = vacationService.createRequest(user, request);
            return ResponseEntity.ok(vacationRequestMapper.toDTO(savedRequest));
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
