package com.example.vacation_service.service;

import com.example.vacation_service.model.Employee;
import com.example.vacation_service.model.VacationRequest;
import com.example.vacation_service.model.RequestStatus;
import com.example.vacation_service.repository.EmployeeRepository;
import com.example.vacation_service.repository.VacationRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class VacationService {

    @Autowired
    private VacationRequestRepository vacationRequestRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    public int getRemainingVacationDays(String employeeId) {
        return employeeRepository.findById(employeeId)
                .map(Employee::getRemainingVacationDays)
                .orElseThrow(() -> new RuntimeException("Employee not found"));
    }

    @Transactional
    public VacationRequest createVacationRequest(VacationRequest request) {
        Employee employee = employeeRepository.findById(request.getAuthor())
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        long vacationDays = ChronoUnit.DAYS.between(
                request.getVacationStartDate(),
                request.getVacationEndDate());

        if (vacationDays > employee.getRemainingVacationDays()) {
            throw new RuntimeException("Insufficient vacation days");
        }

        return vacationRequestRepository.save(request);
    }

    public List<VacationRequest> getAllRequests() {
        return vacationRequestRepository.findAll();
    }

    public List<VacationRequest> getRequestsByStatus(RequestStatus status) {
        return vacationRequestRepository.findByStatus(status);
    }

    public List<VacationRequest> getRequestsByEmployee(String employeeId) {
        return vacationRequestRepository.findByAuthor(employeeId);
    }

    public List<VacationRequest> getOverlappingRequests(LocalDateTime startDate, LocalDateTime endDate) {
        return vacationRequestRepository.findOverlappingRequests(startDate, endDate);
    }

    @Transactional
    public VacationRequest processRequest(Long requestId, String managerId, boolean approved) {
        VacationRequest request = vacationRequestRepository.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Request not found"));

        Employee manager = employeeRepository.findById(managerId)
                .orElseThrow(() -> new RuntimeException("Manager not found"));

        if (!manager.isManager()) {
            throw new RuntimeException("Only managers can process requests");
        }

        request.setStatus(approved ? RequestStatus.APPROVED : RequestStatus.REJECTED);
        request.setResolvedBy(managerId);

        if (approved) {
            Employee employee = employeeRepository.findById(request.getAuthor())
                    .orElseThrow(() -> new RuntimeException("Employee not found"));
            
            long vacationDays = ChronoUnit.DAYS.between(
                    request.getVacationStartDate(),
                    request.getVacationEndDate());
            
            employee.setRemainingVacationDays(
                    employee.getRemainingVacationDays() - (int)vacationDays);
            employeeRepository.save(employee);
        }

        return vacationRequestRepository.save(request);
    }
}
