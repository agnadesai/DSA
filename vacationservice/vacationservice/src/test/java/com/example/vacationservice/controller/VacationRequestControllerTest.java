package com.example.vacationservice.controller;

import com.example.vacationservice.dto.VacationRequestDto;
import com.example.vacationservice.model.User;
import com.example.vacationservice.model.VacationRequest;
import com.example.vacationservice.repository.UserRepository;
import com.example.vacationservice.repository.VacationRequestRepository;
import com.example.vacationservice.security.JwtTokenProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class VacationRequestControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VacationRequestRepository vacationRequestRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvider tokenProvider;

    private String employeeToken;
    private String managerToken;
    private User employee;
    private User manager;

    private String getBaseUrl() {
        return "http://localhost:" + port + "/api/v1/vacation-requests";
    }

    @BeforeEach
    void setUp() {
        vacationRequestRepository.deleteAll();
        userRepository.deleteAll();

        employee = new User();
        employee.setUsername("employee");
        employee.setPassword(passwordEncoder.encode("password"));
        employee.setRole(User.Role.EMPLOYEE);
        employee = userRepository.save(employee);

        manager = new User();
        manager.setUsername("manager");
        manager.setPassword(passwordEncoder.encode("password"));
        manager.setRole(User.Role.MANAGER);
        manager = userRepository.save(manager);

        UserDetails employeeDetails = org.springframework.security.core.userdetails.User.builder()
            .username(employee.getUsername())
            .password(employee.getPassword())
            .authorities(Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + employee.getRole().name())))
            .build();

        UserDetails managerDetails = org.springframework.security.core.userdetails.User.builder()
            .username(manager.getUsername())
            .password(manager.getPassword())
            .authorities(Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + manager.getRole().name())))
            .build();

        employeeToken = tokenProvider.generateToken(new org.springframework.security.authentication.UsernamePasswordAuthenticationToken(
            employeeDetails,
            null,
            employeeDetails.getAuthorities()
        ));
        managerToken = tokenProvider.generateToken(new org.springframework.security.authentication.UsernamePasswordAuthenticationToken(
            managerDetails,
            null,
            managerDetails.getAuthorities()
        ));
    }

    private HttpHeaders createAuthHeaders(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        return headers;
    }

    @Test
    void getMyRequestsShouldReturnUserRequests() {
        VacationRequestDto requestDto = new VacationRequestDto();
        requestDto.setVacationStartDate(LocalDateTime.now().plusDays(1));
        requestDto.setVacationEndDate(LocalDateTime.now().plusDays(5));

        HttpEntity<VacationRequestDto> createRequest = new HttpEntity<>(requestDto, createAuthHeaders(employeeToken));
        restTemplate.postForEntity(getBaseUrl(), createRequest, VacationRequest.class);

        HttpEntity<Void> getRequest = new HttpEntity<>(createAuthHeaders(employeeToken));
        ResponseEntity<VacationRequest[]> response = restTemplate.exchange(
            getBaseUrl() + "/my",
            HttpMethod.GET,
            getRequest,
            VacationRequest[].class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().length).isEqualTo(1);
        assertThat(response.getBody()[0].getAuthor().getUsername()).isEqualTo("employee");
    }

    @Test
    void getAllRequestsShouldReturnAllRequestsForManager() {
        VacationRequestDto requestDto = new VacationRequestDto();
        requestDto.setVacationStartDate(LocalDateTime.now().plusDays(1));
        requestDto.setVacationEndDate(LocalDateTime.now().plusDays(5));

        HttpEntity<VacationRequestDto> employeeRequest = new HttpEntity<>(requestDto, createAuthHeaders(employeeToken));
        restTemplate.postForEntity(getBaseUrl(), employeeRequest, VacationRequest.class);

        HttpEntity<Void> getRequest = new HttpEntity<>(createAuthHeaders(managerToken));
        ResponseEntity<VacationRequest[]> response = restTemplate.exchange(
            getBaseUrl() + "/all",
            HttpMethod.GET,
            getRequest,
            VacationRequest[].class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().length).isGreaterThan(0);
    }

    @Test
    void createRequestShouldReturnCreatedRequest() {
        VacationRequestDto requestDto = new VacationRequestDto();
        requestDto.setVacationStartDate(LocalDateTime.now().plusDays(1));
        requestDto.setVacationEndDate(LocalDateTime.now().plusDays(5));

        HttpEntity<VacationRequestDto> request = new HttpEntity<>(requestDto, createAuthHeaders(employeeToken));
        ResponseEntity<VacationRequest> response = restTemplate.postForEntity(
            getBaseUrl(),
            request,
            VacationRequest.class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getStatus()).isEqualTo(VacationRequest.Status.PENDING);
        assertThat(response.getBody().getAuthor().getUsername()).isEqualTo("employee");
    }

    @Test
    void updateRequestStatusShouldUpdateStatus() {
        VacationRequestDto requestDto = new VacationRequestDto();
        requestDto.setVacationStartDate(LocalDateTime.now().plusDays(1));
        requestDto.setVacationEndDate(LocalDateTime.now().plusDays(5));

        HttpEntity<VacationRequestDto> createRequest = new HttpEntity<>(requestDto, createAuthHeaders(employeeToken));
        ResponseEntity<VacationRequest> createResponse = restTemplate.postForEntity(
            getBaseUrl(),
            createRequest,
            VacationRequest.class
        );

        Long requestId = createResponse.getBody().getId();
        HttpEntity<String> updateRequest = new HttpEntity<>("approve", createAuthHeaders(managerToken));
        ResponseEntity<VacationRequest> response = restTemplate.exchange(
            getBaseUrl() + "/" + requestId + "/status",
            HttpMethod.PATCH,
            updateRequest,
            VacationRequest.class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getStatus()).isEqualTo(VacationRequest.Status.APPROVED);
        assertThat(response.getBody().getResolvedBy().getUsername()).isEqualTo("manager");
    }

    @Test
    void deleteRequestShouldRemoveRequest() {
        VacationRequestDto requestDto = new VacationRequestDto();
        requestDto.setVacationStartDate(LocalDateTime.now().plusDays(1));
        requestDto.setVacationEndDate(LocalDateTime.now().plusDays(5));

        HttpEntity<VacationRequestDto> createRequest = new HttpEntity<>(requestDto, createAuthHeaders(employeeToken));
        ResponseEntity<VacationRequest> createResponse = restTemplate.postForEntity(
            getBaseUrl(),
            createRequest,
            VacationRequest.class
        );

        Long requestId = createResponse.getBody().getId();
        HttpEntity<Void> deleteRequest = new HttpEntity<>(createAuthHeaders(employeeToken));
        ResponseEntity<Void> response = restTemplate.exchange(
            getBaseUrl() + "/" + requestId,
            HttpMethod.DELETE,
            deleteRequest,
            Void.class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

        HttpEntity<Void> getRequest = new HttpEntity<>(createAuthHeaders(employeeToken));
        ResponseEntity<VacationRequest[]> getResponse = restTemplate.exchange(
            getBaseUrl() + "/my",
            HttpMethod.GET,
            getRequest,
            VacationRequest[].class
        );

        assertThat(getResponse.getBody()).isNotNull();
        assertThat(getResponse.getBody().length).isEqualTo(0);
    }

    @Test
    void getOverlappingRequestsShouldReturnOverlappingRequests() {
        VacationRequestDto request1 = new VacationRequestDto();
        request1.setVacationStartDate(LocalDateTime.now().plusDays(1));
        request1.setVacationEndDate(LocalDateTime.now().plusDays(5));

        VacationRequestDto request2 = new VacationRequestDto();
        request2.setVacationStartDate(LocalDateTime.now().plusDays(3));
        request2.setVacationEndDate(LocalDateTime.now().plusDays(7));

        HttpEntity<VacationRequestDto> createRequest = new HttpEntity<>(request1, createAuthHeaders(employeeToken));
        restTemplate.postForEntity(getBaseUrl(), createRequest, VacationRequest.class);

        createRequest = new HttpEntity<>(request2, createAuthHeaders(employeeToken));
        restTemplate.postForEntity(getBaseUrl(), createRequest, VacationRequest.class);

        HttpEntity<Void> getRequest = new HttpEntity<>(createAuthHeaders(managerToken));
        ResponseEntity<VacationRequest[]> response = restTemplate.exchange(
            getBaseUrl() + "/overlapping",
            HttpMethod.GET,
            getRequest,
            VacationRequest[].class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().length).isEqualTo(2);
    }

    @Test
    void getRemainingDaysShouldReturnDaysLeft() {
        HttpEntity<Void> getRequest = new HttpEntity<>(createAuthHeaders(employeeToken));
        ResponseEntity<Integer> response = restTemplate.exchange(
            getBaseUrl() + "/remaining-days",
            HttpMethod.GET,
            getRequest,
            Integer.class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody()).isEqualTo(30); // Initial vacation days
    }
}
