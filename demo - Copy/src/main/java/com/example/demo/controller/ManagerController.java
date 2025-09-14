package com.example.demo.controller;

import com.example.demo.dto.VacationRequestDTO;
import com.example.demo.mapper.VacationRequestMapper;
import com.example.demo.model.RequestStatus;
import com.example.demo.model.User;
import com.example.demo.model.VacationRequest;
import com.example.demo.service.VacationService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/manager")
public class ManagerController {

    private final VacationService vacationService;
    private final VacationRequestMapper vacationRequestMapper;

    public ManagerController(VacationService vacationService, VacationRequestMapper vacationRequestMapper) {
        this.vacationService = vacationService;
        this.vacationRequestMapper = vacationRequestMapper;
    }

    @GetMapping("/requests")
    public List<VacationRequestDTO> getAllRequests() {
        return vacationService.getAllRequests().stream()
                .map(vacationRequestMapper::toDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/requests/pending-approved")
    public List<VacationRequestDTO> getPendingAndApprovedRequests() {
        return vacationService.getPendingAndApprovedRequests().stream()
                .map(vacationRequestMapper::toDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/requests/overlapping")
    public List<VacationRequestDTO> getOverlappingRequests(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {
        return vacationService.getOverlappingRequests(start, end).stream()
                .map(vacationRequestMapper::toDTO)
                .collect(Collectors.toList());
    }

    @PutMapping("/requests/{requestId}")
    public ResponseEntity<VacationRequestDTO> processRequest(
            @PathVariable Long requestId,
            @AuthenticationPrincipal User manager,
            @RequestParam RequestStatus status) {
        try {
            VacationRequest processedRequest = vacationService.processRequest(requestId, manager, status);
            return ResponseEntity.ok(vacationRequestMapper.toDTO(processedRequest));
        } catch (IllegalArgumentException | IllegalStateException e) {
            return ResponseEntity.badRequest().build();
        }
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
}
