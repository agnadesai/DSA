package com.example.vacationservice.repository;

import com.example.vacationservice.model.VacationRequest;
import com.example.vacationservice.model.User;
import com.example.vacationservice.model.User.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class VacationRequestRepositoryTest {

    @Autowired
    private VacationRequestRepository vacationRequestRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    void testFindByStatus() {
        User user = new User();
        user.setUsername("john");
        user.setPassword("password123");
        user.setRole(Role.EMPLOYEE);
        userRepository.save(user);

        VacationRequest request = new VacationRequest();
        request.setAuthor(user);
        request.setVacationStartDate(LocalDateTime.now().plusDays(1));
        request.setVacationEndDate(LocalDateTime.now().plusDays(5));
        request.setStatus(VacationRequest.Status.PENDING);
        vacationRequestRepository.save(request);

        List<VacationRequest> pendingRequests = vacationRequestRepository.findByStatus(VacationRequest.Status.PENDING);

        assertThat(pendingRequests).isNotEmpty();
        assertThat(pendingRequests.get(0).getStatus()).isEqualTo(VacationRequest.Status.PENDING);
    }

    @Test
    void testFindByUser() {
        User user = new User();
        user.setUsername("alice");
        user.setPassword("password123");
        user.setRole(Role.EMPLOYEE);
        userRepository.save(user);

        VacationRequest request = new VacationRequest();
        request.setAuthor(user);
        request.setVacationStartDate(LocalDateTime.now().plusDays(2));
        request.setVacationEndDate(LocalDateTime.now().plusDays(6));
        request.setStatus(VacationRequest.Status.APPROVED);
        vacationRequestRepository.save(request);

        List<VacationRequest> userRequests = vacationRequestRepository.findByAuthor(user);

        assertThat(userRequests).isNotEmpty();
        assertThat(userRequests.get(0).getAuthor().getUsername()).isEqualTo("alice");
    }
}