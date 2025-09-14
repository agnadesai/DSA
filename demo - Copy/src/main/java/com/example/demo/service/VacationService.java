package com.example.demo.service;

import com.example.demo.model.RequestStatus;
import com.example.demo.model.User;
import com.example.demo.model.VacationRequest;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.VacationRequestRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class VacationService {

    private final VacationRequestRepository vacationRequestRepository;
    private final UserRepository userRepository;

    public VacationService(VacationRequestRepository vacationRequestRepository, UserRepository userRepository) {
        this.vacationRequestRepository = vacationRequestRepository;
        this.userRepository = userRepository;
    }

    public VacationRequest getRequestById(Long id) {
        return vacationRequestRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Request not found"));
    }

    public List<VacationRequest> getEmployeeRequests(User employee) {
        return vacationRequestRepository.findByAuthor(employee);
    }

    public List<VacationRequest> getEmployeeRequestsById(Long employeeId) {
        User employee = userRepository.findById(employeeId)
            .orElseThrow(() -> new IllegalArgumentException("Employee not found"));
        return vacationRequestRepository.findByAuthor(employee);
    }

    public List<VacationRequest> getEmployeeRequestsByStatus(User employee, RequestStatus status) {
        return vacationRequestRepository.findByAuthorAndStatus(employee, status);
    }

    public int getRemainingVacationDays(User employee) {
        return employee.getRemainingVacationDays();
    }

    @Transactional
    public VacationRequest createRequest(User employee, VacationRequest request) {
        validateRequest(request);
        
        if (employee.getRemainingVacationDays() < request.getVacationDays()) {
            throw new IllegalStateException("Not enough vacation days remaining");
        }

        List<VacationRequest> overlapping = vacationRequestRepository.findOverlappingRequests(
            request.getVacationStartDate(),
            request.getVacationEndDate()
        );

        if (!overlapping.isEmpty()) {
            throw new IllegalStateException("Request overlaps with existing approved vacations");
        }

        request.setAuthor(employee);
        request.setRequestCreatedAt(LocalDateTime.now());
        return vacationRequestRepository.save(request);
    }

    private void validateRequest(VacationRequest request) {
        if (request.getVacationStartDate() == null || request.getVacationEndDate() == null) {
            throw new IllegalArgumentException("Start and end dates are required");
        }
        
        if (request.getVacationStartDate().isAfter(request.getVacationEndDate())) {
            throw new IllegalArgumentException("Start date must be before end date");
        }
        
        if (request.getVacationStartDate().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Cannot create vacation requests for past dates");
        }
    }

    public List<VacationRequest> getAllRequests() {
        return vacationRequestRepository.findAll();
    }

    public List<VacationRequest> getPendingAndApprovedRequests() {
        return vacationRequestRepository.findAllPendingAndApproved();
    }

    @Transactional
    public VacationRequest processRequest(Long requestId, User manager, RequestStatus newStatus) {
        VacationRequest request = vacationRequestRepository.findById(requestId)
            .orElseThrow(() -> new IllegalArgumentException("Request not found"));

        if (request.getStatus() != RequestStatus.PENDING) {
            throw new IllegalStateException("Request is not pending");
        }

        request.setStatus(newStatus);
        request.setResolvedBy(manager);

        if (newStatus == RequestStatus.APPROVED) {
            User employee = request.getAuthor();
            int daysRequested = request.getVacationDays();
            if (employee.getRemainingVacationDays() < daysRequested) {
                throw new IllegalStateException("Employee does not have enough vacation days");
            }
            employee.setRemainingVacationDays(employee.getRemainingVacationDays() - daysRequested);
            userRepository.save(employee);
        }

        return vacationRequestRepository.save(request);
    }

    public List<VacationRequest> getOverlappingRequests(LocalDateTime start, LocalDateTime end) {
        return vacationRequestRepository.findOverlappingRequests(start, end);
    }
}
