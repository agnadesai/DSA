package com.example.vacationservice.controller;

import com.example.vacationservice.model.VacationRequest;
import com.example.vacationservice.service.VacationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/requests")
public class ManagerController {

    @Autowired
    private VacationService vacationService;

    @GetMapping("/all")
    public ResponseEntity<List<VacationRequest>> getAllRequests(
          @Valid @RequestParam(required = false) VacationRequest.Status status) {
        List<VacationRequest> requests = status != null ?
                vacationService.getRequestsByStatus(status) :
                vacationService.getAllRequests();
        return ResponseEntity.ok(requests);
    }

    @GetMapping("/overlapping")
    public ResponseEntity<List<VacationRequest>> getOverlappingRequests(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        List<VacationRequest> requests = vacationService.getOverlappingRequests(startDate, endDate);
        return ResponseEntity.ok(requests);
    }

    @PutMapping("/{requestId}/{status}")
    public ResponseEntity<VacationRequest> approveRequest(
            @PathVariable Long requestId,
            @PathVariable String status,
            @AuthenticationPrincipal UserDetails userDetails) {
        boolean approved = false;
        if(status.toLowerCase().startsWith("approve")) {
            approved = true;
        }
        VacationRequest request = vacationService.processRequest(requestId, userDetails.getUsername(), approved);
        return ResponseEntity.ok(request);
    }

    @GetMapping("/employee/{username}/requests")
    public ResponseEntity<List<VacationRequest>> getEmployeeRequests(@PathVariable String username) {
        List<VacationRequest> requests = vacationService.getUserRequests(username);
        return ResponseEntity.ok(requests);
    }

}
