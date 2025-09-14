package com.example.vacationservice.controller;

import com.example.vacationservice.dto.VacationRequestDto;
import com.example.vacationservice.exception.BadVacationRequestException;
import com.example.vacationservice.model.VacationRequest;
import com.example.vacationservice.service.VacationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/*
Further improvements
- add pagination for the requests
- add client and server side cache
- put behind api gateway
- apply rate limiting for ddos attacks
- set connection pool for database access
- add asynchronous logging
 */
@RestController
@RequestMapping("/api/v1/vacation-requests")
@Tag(name = "Vacation Requests", description = "Vacation request management operations")
public class VacationRequestController {

    @Autowired
    private VacationService vacationService;

    @Operation(summary = "Get my vacation requests", description = "Get all vacation requests for the authenticated user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved requests"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @GetMapping("/my")
    @PreAuthorize("hasRole('EMPLOYEE')")
    public ResponseEntity<List<VacationRequest>> getMyRequests(
            @Parameter(hidden = true)
            @AuthenticationPrincipal UserDetails userDetails,
            @Valid @RequestParam(required = false) String status,
            @Valid @RequestParam(required = false) Integer limit,
            @Valid @RequestParam(required = false) Integer offset) {

        if (status != null && !(status.equalsIgnoreCase("approved") || status.equalsIgnoreCase("rejected") || status.equalsIgnoreCase("pending"))) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Status has to be either APPROVED, PENDING, REJECTED");
        }
        if(limit != null || offset != null) {
            List<VacationRequest> vacationRequests = vacationService.getUserRequests(userDetails.getUsername(), status, limit, offset).stream().collect(Collectors.toList());
            return ResponseEntity.ok(vacationRequests);
        }

        List<VacationRequest> requests = status != null ?
                vacationService.getRequestByAuthorAndStatus(userDetails.getUsername(), VacationRequest.Status.valueOf(status.toUpperCase())):
                vacationService.getUserRequests(userDetails.getUsername());

        return ResponseEntity.ok(requests);
    }

    @Operation(summary = "Get all vacation requests", description = "Retrieve all vacation requests with optional status filter")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved requests"),
            @ApiResponse(responseCode = "403", description = "Access denied")
    })
    @GetMapping("/all")
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<List<VacationRequest>> getAllRequests(
            @Parameter(description = "Filter requests by status (PENDING/APPROVED/REJECTED)")
            @Valid @RequestParam(required = false) VacationRequest.Status status) {
        List<VacationRequest> requests = status != null ?
                vacationService.getRequestsByStatus(status) :
                vacationService.getAllRequests();
        return ResponseEntity.ok(requests);
    }

    @Operation(summary = "Get overlapping requests", description = "Find all vacation requests that overlap with each other")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved overlapping requests"),
            @ApiResponse(responseCode = "403", description = "Access denied for non-manager users")
    })
    @GetMapping("/overlapping")
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<List<VacationRequest>> getOverlappingRequests() {
        List<VacationRequest> requests = vacationService.getOverlappingRequests();
        return ResponseEntity.ok(requests);
    }

    @Operation(summary = "Get employee requests", description = "Get all vacation requests for a specific employee")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved employee requests"),
            @ApiResponse(responseCode = "404", description = "Employee not found"),
            @ApiResponse(responseCode = "403", description = "Access denied for non-manager users")
    })
    @GetMapping("/employee/{username}")
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<List<VacationRequest>> getEmployeeRequests(
            @Parameter(description = "Username to get requests for")
            @PathVariable String username) {
        List<VacationRequest> requests = vacationService.getUserRequests(username);
        return ResponseEntity.ok(requests);
    }

    @Operation(summary = "Get remaining vacation days", description = "Get the number of remaining vacation days for the authenticated user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved remaining days"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @GetMapping("/remaining-days")
    @PreAuthorize("hasRole('EMPLOYEE')")
    public ResponseEntity<Integer> getRemainingDays(
            @Parameter(hidden = true)
            @AuthenticationPrincipal UserDetails userDetails) {
        int remainingDays = vacationService.getRemainingVacationDays(userDetails.getUsername());
        return ResponseEntity.ok(remainingDays);
    }

    @Operation(summary = "Create vacation request", description = "Create a new vacation request for the authenticated user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created request"),
            @ApiResponse(responseCode = "400", description = "Invalid request (e.g., not enough vacation days)"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @PostMapping
    @PreAuthorize("hasRole('EMPLOYEE')")
    public ResponseEntity<VacationRequest> createRequest(
            @Parameter(description = "Vacation request details")
            @Valid @RequestBody VacationRequestDto requestDto,
            @Parameter(hidden = true)
            @AuthenticationPrincipal UserDetails userDetails) {
        if (!requestDto.getVacationStartDate().isAfter(LocalDateTime.now())) {
            throw new BadVacationRequestException(HttpStatus.BAD_REQUEST.toString(), "Vacation start date must be in the future");
           // throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Vacation start date must be in the future");
        }
        VacationRequest request = vacationService.createRequest(userDetails.getUsername(), requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(request);
    }

    @Operation(summary = "Delete vacation request", description = "Delete a vacation request by ID for the authenticated user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successfully deleted request"),
            @ApiResponse(responseCode = "404", description = "Vacation request not found"),
            @ApiResponse(responseCode = "403", description = "User not authorized to delete this request")
    })

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('EMPLOYEE')")
    public ResponseEntity<Void> deleteRequest(
            @Parameter(description = "ID of the vacation request to delete")
            @PathVariable Long id,
            @Parameter(hidden = true)
            @AuthenticationPrincipal UserDetails userDetails) {
        vacationService.deleteRequestById(userDetails.getUsername(), id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Update vacation request status", description = "Update the status of a vacation request by providing the status in the request body")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated request status"),
            @ApiResponse(responseCode = "404", description = "Request not found"),
            @ApiResponse(responseCode = "400", description = "Invalid status provided"),
            @ApiResponse(responseCode = "403", description = "Access denied")
    })
    @PatchMapping("/{requestId}/status")
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<VacationRequest> updateRequestStatus(
            @Parameter(description = "ID of the vacation request to update")
            @PathVariable Long requestId,
            @Parameter(description = "Status to set (approve/reject)")
            @RequestBody String status,
            @AuthenticationPrincipal UserDetails userDetails) {

        if (!status.equalsIgnoreCase("approve") && !status.equalsIgnoreCase("reject")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid status. Use 'approve' or 'reject'.");
        }

        boolean approved = status.equalsIgnoreCase("approve");
        VacationRequest request = vacationService.processRequest(requestId, userDetails.getUsername(), approved);
        return ResponseEntity.ok(request);
    }

}
