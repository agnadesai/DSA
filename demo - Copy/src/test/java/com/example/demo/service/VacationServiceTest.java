package com.example.demo.service;

import com.example.demo.model.RequestStatus;
import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.model.VacationRequest;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.VacationRequestRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class VacationServiceTest {

    @Mock
    private VacationRequestRepository vacationRequestRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private VacationService vacationService;

    private User employee;
    private User manager;
    private VacationRequest request;

    @BeforeEach
    void setUp() {
        employee = new User();
        employee.setId(1L);
        employee.setUsername("employee");
        employee.setRole(Role.EMPLOYEE);
        employee.setRemainingVacationDays(30);

        manager = new User();
        manager.setId(2L);
        manager.setUsername("manager");
        manager.setRole(Role.MANAGER);

        request = new VacationRequest();
        request.setId(1L);
        request.setAuthor(employee);
        request.setVacationStartDate(LocalDateTime.now().plusDays(1));
        request.setVacationEndDate(LocalDateTime.now().plusDays(5));
        request.setStatus(RequestStatus.PENDING);
    }

    @Test
    void createRequest_Success() {
        when(vacationRequestRepository.save(any(VacationRequest.class))).thenReturn(request);

        VacationRequest result = vacationService.createRequest(employee, request);

        assertNotNull(result);
        assertEquals(employee, result.getAuthor());
        assertEquals(RequestStatus.PENDING, result.getStatus());
        verify(vacationRequestRepository).save(any(VacationRequest.class));
    }

    @Test
    void createRequest_NotEnoughDays() {
        employee.setRemainingVacationDays(3);
        request.setVacationEndDate(LocalDateTime.now().plusDays(10));

        assertThrows(IllegalStateException.class, () -> {
            vacationService.createRequest(employee, request);
        });
    }

    @Test
    void processRequest_Approve() {
        when(vacationRequestRepository.findById(1L)).thenReturn(Optional.of(request));
        when(vacationRequestRepository.save(any(VacationRequest.class))).thenReturn(request);
        when(userRepository.save(any(User.class))).thenReturn(employee);

        VacationRequest result = vacationService.processRequest(1L, manager, RequestStatus.APPROVED);

        assertNotNull(result);
        assertEquals(RequestStatus.APPROVED, result.getStatus());
        assertEquals(manager, result.getResolvedBy());
        verify(userRepository).save(employee);
    }

    @Test
    void processRequest_AlreadyProcessed() {
        request.setStatus(RequestStatus.APPROVED);
        when(vacationRequestRepository.findById(1L)).thenReturn(Optional.of(request));

        assertThrows(IllegalStateException.class, () -> {
            vacationService.processRequest(1L, manager, RequestStatus.REJECTED);
        });
    }
}
