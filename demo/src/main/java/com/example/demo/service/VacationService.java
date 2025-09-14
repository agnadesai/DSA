package com.example.demo.service;

import com.example.demo.model.RequestStatus;
import com.example.demo.model.User;
import com.example.demo.model.VacationRequest;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.VacationRequestRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class VacationService {

    private final VacationRequestRepository vacationRequestRepository;
    private final UserRepository userRepository;

    public VacationService(VacationRequestRepository vacationRequestRepository, UserRepository userRepository) {
        this.vacationRequestRepository = vacationRequestRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public VacationRequest createRequest(User employee, VacationRequest request) {
        User currentUser = getCurrentUser();
        
        if (!currentUser.equals(employee)) {
            throw new IllegalStateException("Cannot create request for another user");
        }
        
        // Set the author
        request.setAuthor(employee);
        request.setRequestCreatedAt(LocalDateTime.now());
        request.setStatus(RequestStatus.PENDING);
        
        // Calculate vacation days
        int vacationDays = (int) (ChronoUnit.DAYS.between(request.getVacationStartDate(), request.getVacationEndDate()) + 1);
        request.setVacationDays(vacationDays);
        
        // Validate remaining vacation days
        int usedDays = getUserUsedVacationDays(employee);
        long remainingDays = employee.getRemainingVacationDays();
        
        if (vacationDays > remainingDays) {
            throw new IllegalStateException("Insufficient vacation days. You have " + remainingDays + " days remaining.");
        }
        
        // Check for overlapping requests
        List<VacationRequest> overlappingRequests = vacationRequestRepository.findByAuthorAndDateRange(
            employee,
            request.getVacationStartDate(),
            request.getVacationEndDate()
        );
        
        if (!overlappingRequests.isEmpty()) {
            throw new IllegalStateException("You already have a vacation request for these dates.");
        }
        
        return vacationRequestRepository.save(request);
    }

    @Transactional
    public VacationRequest processRequest(Long requestId, User manager, RequestStatus status) {
        if (!manager.equals(getCurrentUser())) {
            throw new IllegalStateException("Only the current user can process the request");
        }
        
        VacationRequest request = vacationRequestRepository.findById(requestId)
            .orElseThrow(() -> new IllegalArgumentException("Request not found"));
            
        if (request.getStatus() != RequestStatus.PENDING) {
            throw new IllegalStateException("Request has already been processed");
        }
        
        request.setStatus(status);
        request.setResolvedBy(manager);
        request.setResolvedAt(LocalDateTime.now());
        
        // Update remaining vacation days if approved
        if (status == RequestStatus.APPROVED) {
            User employee = request.getAuthor();
            long remainingDays = employee.getRemainingVacationDays() - request.getVacationDays();
            if (remainingDays < 0) {
                throw new IllegalStateException("Employee does not have enough vacation days");
            }
            employee.setRemainingVacationDays(remainingDays);
            userRepository.save(employee);
        }
        
        return vacationRequestRepository.save(request);
    }

    private User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return userRepository.findByUsername(auth.getName())
            .orElseThrow(() -> new IllegalStateException("Current user not found"));
    }

    private int getUserUsedVacationDays(User user) {
        List<VacationRequest> approvedRequests = vacationRequestRepository
            .findByAuthorAndStatus(user, RequestStatus.APPROVED);
            
        return approvedRequests.stream()
            .mapToInt(VacationRequest::getVacationDays)
            .sum();
    }

    public List<VacationRequest> getAllRequests() {
        return vacationRequestRepository.findAll();
    }

    public List<VacationRequest> getUserRequests(User user) {
        return vacationRequestRepository.findByAuthor(user);
    }

    public List<VacationRequest> getUserRequestsByStatus(User user, RequestStatus status) {
        return vacationRequestRepository.findByAuthorAndStatus(user, status);
    }

    public VacationRequest getRequest(Long id) {
        return vacationRequestRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Request not found"));
    }

    public long getRemainingVacationDays(User employee) {
        return employee.getRemainingVacationDays();
    }

    public List<VacationRequest> getPendingAndApprovedRequests() {
        return vacationRequestRepository.findAllPendingAndApproved();
    }

    public List<VacationRequest> getOverlappingRequests(LocalDateTime start, LocalDateTime end) {
        return vacationRequestRepository.findByDateRange(start, end);
    }

    public List<VacationRequest> getEmployeeRequestsById(Long employeeId) {
        User employee = userRepository.findById(employeeId)
            .orElseThrow(() -> new IllegalArgumentException("Employee not found"));
        return getUserRequests(employee);
    }
}
