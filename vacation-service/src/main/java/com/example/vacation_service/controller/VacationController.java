package com.example.vacation_service.controller;

import com.example.vacation_service.model.VacationRequest;
import com.example.vacation_service.model.RequestStatus;
import com.example.vacation_service.service.VacationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/vacation")
public class VacationController {

    @Autowired
    private VacationService vacationService;

    @GetMapping("/remaining-days/{employeeId}")
    @PreAuthorize("hasRole('EMPLOYEE') and #employeeId == authentication.name or hasRole('MANAGER')")
    public ResponseEntity<Integer> getRemainingDays(@PathVariable String employeeId) {
        return ResponseEntity.ok(vacationService.getRemainingVacationDays(employeeId));
    }

    @PostMapping("/request")
    @PreAuthorize("hasRole('EMPLOYEE') and #request.author == authentication.name")
    public ResponseEntity<VacationRequest> createRequest(@RequestBody VacationRequest request) {
        return ResponseEntity.ok(vacationService.createVacationRequest(request));
    }

    @GetMapping("/requests")
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<List<VacationRequest>> getAllRequests(
            @RequestParam(required = false) RequestStatus status) {
        if (status != null) {
            return ResponseEntity.ok(vacationService.getRequestsByStatus(status));
        }
        return ResponseEntity.ok(vacationService.getAllRequests());
    }

    @GetMapping("/requests/employee/{employeeId}")
    @PreAuthorize("hasRole('MANAGER') or (hasRole('EMPLOYEE') and #employeeId == authentication.name)")
    public ResponseEntity<List<VacationRequest>> getEmployeeRequests(
            @PathVariable String employeeId) {
        return ResponseEntity.ok(vacationService.getRequestsByEmployee(employeeId));
    }

    @GetMapping("/requests/overlapping")
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<List<VacationRequest>> getOverlappingRequests(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        return ResponseEntity.ok(vacationService.getOverlappingRequests(startDate, endDate));
    }

    @PutMapping("/requests/{requestId}/process")
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<VacationRequest> processRequest(
            @PathVariable Long requestId,
            @RequestParam String managerId,
            @RequestParam boolean approved) {
        return ResponseEntity.ok(vacationService.processRequest(requestId, managerId, approved));
    }
}
