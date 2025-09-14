package com.example.vacation_service;

import com.example.vacation_service.model.Employee;
import com.example.vacation_service.model.VacationRequest;
import com.example.vacation_service.model.RequestStatus;
import com.example.vacation_service.repository.EmployeeRepository;
import com.example.vacation_service.service.VacationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class VacationServiceTests {

//    @Autowired
//    private VacationService vacationService;
//
//    @Autowired
//    private EmployeeRepository employeeRepository;
//
//    private Employee employee;
//    private Employee manager;
//
//    @BeforeEach
//    void setUp() {
//        // Create test employee
//        employee = new Employee();
//        employee.setId("EMP001");
//        employee.setManager(false);
//        employee.setRemainingVacationDays(30);
//        employeeRepository.save(employee);
//
//        // Create test manager
//        manager = new Employee();
//        manager.setId("MGR001");
//        manager.setManager(true);
//        manager.setRemainingVacationDays(30);
//        employeeRepository.save(manager);
//    }
//
//    @Test
//    void testCreateVacationRequest() {
//        VacationRequest request = new VacationRequest();
//        request.setAuthor("EMP001");
//        request.setVacationStartDate(LocalDateTime.now().plusDays(1));
//        request.setVacationEndDate(LocalDateTime.now().plusDays(5));
//
//        VacationRequest savedRequest = vacationService.createVacationRequest(request);
//
//        assertNotNull(savedRequest.getId());
//        assertEquals(RequestStatus.PENDING, savedRequest.getStatus());
//        assertEquals("EMP001", savedRequest.getAuthor());
//    }
//
//    @Test
//    void testProcessVacationRequest() {
//        // Create a vacation request
//        VacationRequest request = new VacationRequest();
//        request.setAuthor("EMP001");
//        request.setVacationStartDate(LocalDateTime.now().plusDays(1));
//        request.setVacationEndDate(LocalDateTime.now().plusDays(5));
//        VacationRequest savedRequest = vacationService.createVacationRequest(request);
//
//        // Process the request
//        VacationRequest processedRequest = vacationService.processRequest(
//                savedRequest.getId(), "MGR001", true);
//
//        assertEquals(RequestStatus.APPROVED, processedRequest.getStatus());
//        assertEquals("MGR001", processedRequest.getResolvedBy());
//
//        // Check remaining vacation days
//        Employee updatedEmployee = employeeRepository.findById("EMP001").orElseThrow();
//        assertEquals(26, updatedEmployee.getRemainingVacationDays()); // 30 - 4 days
//    }
//
//    @Test
//    void testInsufficientVacationDays() {
//        VacationRequest request = new VacationRequest();
//        request.setAuthor("EMP001");
//        request.setVacationStartDate(LocalDateTime.now().plusDays(1));
//        request.setVacationEndDate(LocalDateTime.now().plusDays(35)); // More than available days
//
//        assertThrows(RuntimeException.class, () -> {
//            vacationService.createVacationRequest(request);
//        });
//    }
}
