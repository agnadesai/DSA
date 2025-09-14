package com.example.demo.controller;

import com.example.demo.dto.VacationRequestDTO;
import com.example.demo.mapper.VacationRequestMapper;
import com.example.demo.model.RequestStatus;
import com.example.demo.model.User;
import com.example.demo.model.VacationRequest;
import com.example.demo.service.UserService;
import com.example.demo.service.VacationService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/manager")
@PreAuthorize("hasRole('MANAGER')")
public class ManagerController {

    private final VacationService vacationService;
    private final VacationRequestMapper vacationRequestMapper;
    private final UserService userService;

    public ManagerController(VacationService vacationService, VacationRequestMapper vacationRequestMapper, UserService userService) {
        this.vacationService = vacationService;
        this.vacationRequestMapper = vacationRequestMapper;
        this.userService = userService;
    }

    @GetMapping("/requests")
    public ResponseEntity<List<VacationRequestDTO>> getAllRequests() {
        return ResponseEntity.ok(vacationService.getAllRequests().stream()
                .map(vacationRequestMapper::toDTO)
                .collect(Collectors.toList()));
    }

    @GetMapping("/requests/pending-approved")
    public ResponseEntity<List<VacationRequestDTO>> getPendingAndApprovedRequests() {
        return ResponseEntity.ok(vacationService.getPendingAndApprovedRequests().stream()
                .map(vacationRequestMapper::toDTO)
                .collect(Collectors.toList()));
    }

    @GetMapping("/requests/overlapping")
    public ResponseEntity<List<VacationRequestDTO>> getOverlappingRequests(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {
        return ResponseEntity.ok(vacationService.getOverlappingRequests(start, end).stream()
                .map(vacationRequestMapper::toDTO)
                .collect(Collectors.toList()));
    }

    @GetMapping("/requests/employee/{employeeId}")
    public ResponseEntity<List<VacationRequestDTO>> getEmployeeRequests(@PathVariable Long employeeId) {
        try {
            return ResponseEntity.ok(
                vacationService.getEmployeeRequestsById(employeeId).stream()
                    .map(vacationRequestMapper::toDTO)
                    .collect(Collectors.toList())
            );
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/request/{id}/process")
    public ResponseEntity<VacationRequestDTO> processRequest(
            @PathVariable Long id,
            @RequestParam RequestStatus status,
            @AuthenticationPrincipal User manager) {
        try {
            VacationRequest processed = vacationService.processRequest(id, manager, status);
            return ResponseEntity.ok(vacationRequestMapper.toDTO(processed));
        } catch (IllegalStateException | IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
