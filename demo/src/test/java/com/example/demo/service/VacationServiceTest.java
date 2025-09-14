package com.example.demo.service;

import com.example.demo.model.RequestStatus;
import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.model.VacationRequest;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.VacationRequestRepository;
import org.junit.Ignore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@Ignore
@ExtendWith(MockitoExtension.class)
class VacationServiceTest {

//    @Mock
//    private VacationRequestRepository vacationRequestRepository;
//
//    @Mock
//    private UserRepository userRepository;
//
//    @Mock
//    private Authentication authentication;
//
//    @Mock
//    private SecurityContext securityContext;
//
//    @InjectMocks
//    private VacationService vacationService;
//
//    private User employee;
//    private User manager;
//    private VacationRequest request;
//
//    @BeforeEach
//    void setUp() {
//        employee = new User();
//        employee.setId(1L);
//        employee.setUsername("employee");
//        employee.setPassword("password");
//        employee.setRole(Role.EMPLOYEE);
//        employee.setRemainingVacationDays(30);
//
//        manager = new User();
//        manager.setId(2L);
//        manager.setUsername("manager");
//        manager.setPassword("password");
//        manager.setRole(Role.MANAGER);
//
//        request = new VacationRequest();
//        request.setId(1L);
//        request.setAuthor(employee);
//        request.setVacationStartDate(LocalDateTime.now().plusDays(1));
//        request.setVacationEndDate(LocalDateTime.now().plusDays(5));
//        request.setVacationDays(5);
//        request.setStatus(RequestStatus.PENDING);
//
////        when(securityContext.getAuthentication()).thenReturn(authentication);
//        SecurityContextHolder.setContext(securityContext);
//    }
//
//    @Test
//    @Ignore
//    void createRequest_Success() {
//        when(authentication.getName()).thenReturn("employee");
//        when(userRepository.findByUsername("employee")).thenReturn(Optional.of(employee));
//        when(vacationRequestRepository.findByAuthorAndDateRange(any(), any(), any())).thenReturn(List.of());
//        when(vacationRequestRepository.save(any())).thenReturn(request);
//
//        VacationRequest result = vacationService.createRequest(employee, request);
//
//        assertNotNull(result);
//        assertEquals(RequestStatus.PENDING, result.getStatus());
//        assertEquals(employee, result.getAuthor());
//        verify(vacationRequestRepository).save(any());
//    }
//
//    @Test
//    @Ignore
//    void createRequest_InsufficientDays() {
//        employee.setRemainingVacationDays(3);
//        when(authentication.getName()).thenReturn("employee");
//        when(userRepository.findByUsername("employee")).thenReturn(Optional.of(employee));
//
//        assertThrows(IllegalStateException.class, () ->
//            vacationService.createRequest(employee, request)
//        );
//    }
//
//    @Test
//    @Ignore
//    void processRequest_Approved() {
//        when(authentication.getName()).thenReturn("manager");
//        when(userRepository.findByUsername("manager")).thenReturn(Optional.of(manager));
//        when(vacationRequestRepository.findById(1L)).thenReturn(Optional.of(request));
//        when(vacationRequestRepository.save(any())).thenReturn(request);
//        when(userRepository.save(any())).thenReturn(employee);
//
//        VacationRequest result = vacationService.processRequest(1L, manager, RequestStatus.APPROVED);
//
//        assertNotNull(result);
//        assertEquals(RequestStatus.APPROVED, result.getStatus());
//        assertEquals(manager, result.getResolvedBy());
//        assertEquals(25, employee.getRemainingVacationDays());
//    }
//
//    @Test
//    @Ignore
//    void getUserRequests() {
//        List<VacationRequest> requests = Arrays.asList(request);
//        when(vacationRequestRepository.findByAuthor(employee)).thenReturn(requests);
//
//        List<VacationRequest> result = vacationService.getUserRequests(employee);
//
//        assertThat(result).hasSize(1);
//        assertEquals(request, result.get(0));
//    }
//
//    @Test
//    @Ignore
//    void getUserRequestsByStatus() {
//        List<VacationRequest> requests = Arrays.asList(request);
//        when(vacationRequestRepository.findByAuthorAndStatus(employee, RequestStatus.PENDING))
//            .thenReturn(requests);
//
//        List<VacationRequest> result = vacationService.getUserRequestsByStatus(employee, RequestStatus.PENDING);
//
//        assertThat(result).hasSize(1);
//        assertEquals(RequestStatus.PENDING, result.get(0).getStatus());
//    }
//
//    @Test
//    @Ignore
//    void getPendingAndApprovedRequests() {
//        List<VacationRequest> requests = Arrays.asList(request);
//        when(vacationRequestRepository.findAllPendingAndApproved()).thenReturn(requests);
//
//        List<VacationRequest> result = vacationService.getPendingAndApprovedRequests();
//
//        assertThat(result).hasSize(1);
//        assertTrue(result.get(0).getStatus() == RequestStatus.PENDING ||
//                  result.get(0).getStatus() == RequestStatus.APPROVED);
//    }
//
//    @Test
//    @Ignore
//    void getOverlappingRequests() {
//        LocalDateTime start = LocalDateTime.now();
//        LocalDateTime end = LocalDateTime.now().plusDays(7);
//        List<VacationRequest> requests = Arrays.asList(request);
//        when(vacationRequestRepository.findByDateRange(start, end)).thenReturn(requests);
//
//        List<VacationRequest> result = vacationService.getOverlappingRequests(start, end);
//
//        assertThat(result).hasSize(1);
//        assertTrue(result.get(0).getVacationStartDate().isBefore(end) &&
//                  result.get(0).getVacationEndDate().isAfter(start));
//    }
//
//    @Test
//    @Ignore
//    void getEmployeeRequestsById() {
//        when(userRepository.findById(1L)).thenReturn(Optional.of(employee));
//        List<VacationRequest> requests = Arrays.asList(request);
//        when(vacationRequestRepository.findByAuthor(employee)).thenReturn(requests);
//
//        List<VacationRequest> result = vacationService.getEmployeeRequestsById(1L);
//
//        assertThat(result).hasSize(1);
//        assertEquals(employee, result.get(0).getAuthor());
//    }
//
//    @Test
//    @Ignore
//    void processRequest_AlreadyProcessed() {
//        request.setStatus(RequestStatus.APPROVED);
//        when(authentication.getName()).thenReturn("manager");
//        when(userRepository.findByUsername("manager")).thenReturn(Optional.of(manager));
//        when(vacationRequestRepository.findById(1L)).thenReturn(Optional.of(request));
//
//        assertThrows(IllegalStateException.class, () ->
//            vacationService.processRequest(1L, manager, RequestStatus.REJECTED)
//        );
//    }
//
//    @Test
//    @Ignore
//    void createRequest_OverlappingDates() {
//        when(authentication.getName()).thenReturn("employee");
//        when(userRepository.findByUsername("employee")).thenReturn(Optional.of(employee));
//        when(vacationRequestRepository.findByAuthorAndDateRange(any(), any(), any()))
//            .thenReturn(Arrays.asList(request));
//
//        VacationRequest newRequest = new VacationRequest();
//        newRequest.setVacationStartDate(LocalDateTime.now().plusDays(2));
//        newRequest.setVacationEndDate(LocalDateTime.now().plusDays(4));
//
//        assertThrows(IllegalStateException.class, () ->
//            vacationService.createRequest(employee, newRequest)
//        );
//    }
}
