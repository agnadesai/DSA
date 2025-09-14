package com.example.vacationservice.service;

import com.example.vacationservice.dto.VacationRequestDto;
import com.example.vacationservice.exception.BadVacationRequestException;
import com.example.vacationservice.model.User;
import com.example.vacationservice.model.VacationRequest;
import com.example.vacationservice.repository.UserRepository;
import com.example.vacationservice.repository.VacationRequestRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VacationServiceTest {

    @Mock
    private VacationRequestRepository vacationRequestRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private VacationService vacationService;

    @Test
    void testCreateRequest_Success() {
        String username = "testuser";
        VacationRequestDto requestDto = new VacationRequestDto();
        requestDto.setVacationStartDate(LocalDateTime.now().plusDays(1));
        requestDto.setVacationEndDate(LocalDateTime.now().plusDays(5));

        User user = new User();
        user.setUsername(username);
        user.setRemainingVacationDays(10);

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));
        when(vacationRequestRepository.save(any(VacationRequest.class))).thenAnswer(invocation -> invocation.getArgument(0));

        VacationRequest createdRequest = vacationService.createRequest(username, requestDto);

        assertThat(createdRequest.getAuthor()).isEqualTo(user);
        assertThat(createdRequest.getVacationStartDate()).isEqualTo(requestDto.getVacationStartDate());
        assertThat(createdRequest.getVacationEndDate()).isEqualTo(requestDto.getVacationEndDate());
        verify(userRepository).save(user);
        verify(vacationRequestRepository).save(any(VacationRequest.class));
    }

    @Test
    void testCreateRequest_NotEnoughVacationDays() {
        String username = "testuser";
        VacationRequestDto requestDto = new VacationRequestDto();
        requestDto.setVacationStartDate(LocalDateTime.now().plusDays(1));
        requestDto.setVacationEndDate(LocalDateTime.now().plusDays(15));

        User user = new User();
        user.setUsername(username);
        user.setRemainingVacationDays(5);

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));

        BadVacationRequestException exception = assertThrows(BadVacationRequestException.class, () -> vacationService.createRequest(username, requestDto));
        assertThat(exception.getMessage()).isEqualTo("Not enough vacation days remaining");
        verify(userRepository, never()).save(any(User.class));
        verify(vacationRequestRepository, never()).save(any(VacationRequest.class));
    }

    @Test
    void testCreateRequest_UserNotFound() {
        String username = "nonexistentuser";
        VacationRequestDto requestDto = new VacationRequestDto();
        requestDto.setVacationStartDate(LocalDateTime.now().plusDays(1));
        requestDto.setVacationEndDate(LocalDateTime.now().plusDays(5));

        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());

        UsernameNotFoundException exception = assertThrows(UsernameNotFoundException.class, () -> vacationService.createRequest(username, requestDto));
        assertThat(exception.getMessage()).isEqualTo("User not found");
        verify(vacationRequestRepository, never()).save(any(VacationRequest.class));
    }
}