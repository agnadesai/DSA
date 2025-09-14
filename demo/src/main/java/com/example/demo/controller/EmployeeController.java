package com.example.demo.controller;

import com.example.demo.dto.VacationRequestDTO;
import com.example.demo.mapper.VacationRequestMapper;
import com.example.demo.model.RequestStatus;
import com.example.demo.model.User;
import com.example.demo.model.VacationRequest;
import com.example.demo.service.UserService;
import com.example.demo.service.VacationService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/employee")
public class EmployeeController {

    private final VacationService vacationService;
    private final VacationRequestMapper vacationRequestMapper;
    private final UserService userService;

    public EmployeeController(VacationService vacationService, VacationRequestMapper vacationRequestMapper, UserService userService) {
        this.vacationService = vacationService;
        this.vacationRequestMapper = vacationRequestMapper;
        this.userService = userService;
    }

    @GetMapping("/requests")
    public ResponseEntity<List<VacationRequestDTO>> getMyRequests(@AuthenticationPrincipal User user) {
        User employee = userService.getUserByUsername(user.getUsername());
        return ResponseEntity.ok(vacationService.getUserRequests(employee).stream()
                .map(vacationRequestMapper::toDTO)
                .collect(Collectors.toList()));
    }

    @GetMapping("/requests/filter")
    public ResponseEntity<List<VacationRequestDTO>> getMyRequestsByStatus(
            @AuthenticationPrincipal User user,
            @RequestParam RequestStatus status) {
        User employee = userService.getUserByUsername(user.getUsername());
        return ResponseEntity.ok(vacationService.getUserRequestsByStatus(employee, status).stream()
                .map(vacationRequestMapper::toDTO)
                .collect(Collectors.toList()));
    }

    @GetMapping("/vacation-days")
    public ResponseEntity<Map<String, Long>> getRemainingVacationDays(@AuthenticationPrincipal User user) {
        User employee = userService.getUserByUsername(user.getUsername());
        return ResponseEntity.ok(Map.of("remainingDays", vacationService.getRemainingVacationDays(employee)));
    }

    @PostMapping("/requests")
    public ResponseEntity<VacationRequestDTO> createRequest(
            @AuthenticationPrincipal User user,
            @Valid @RequestBody VacationRequest request) {
        User employee = userService.getUserByUsername(user.getUsername());
        try {
            VacationRequest savedRequest = vacationService.createRequest(employee, request);
            return ResponseEntity.ok(vacationRequestMapper.toDTO(savedRequest));
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
