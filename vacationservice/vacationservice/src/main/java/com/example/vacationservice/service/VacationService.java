package com.example.vacationservice.service;

import com.example.vacationservice.dto.VacationRequestDto;
import com.example.vacationservice.exception.BadVacationRequestException;
import com.example.vacationservice.model.User;
import com.example.vacationservice.model.VacationRequest;
import com.example.vacationservice.repository.UserRepository;
import com.example.vacationservice.repository.VacationRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class VacationService {

    @Autowired
    private VacationRequestRepository vacationRequestRepository;

    @Autowired
    private UserRepository userRepository;

    public VacationRequest createRequest(String username, VacationRequestDto requestDto) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        int daysRequested = (int) Duration.between(
                requestDto.getVacationStartDate(),
                requestDto.getVacationEndDate()
        ).toDays() + 1;

        if (daysRequested > user.getRemainingVacationDays()) {
            throw new BadVacationRequestException("Not enough vacation days remaining");
            //throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Not enough vacation days remaining");
        }

        VacationRequest request = new VacationRequest();
        request.setAuthor(user);
        request.setVacationStartDate(requestDto.getVacationStartDate());
        request.setVacationEndDate(requestDto.getVacationEndDate());

        user.setRemainingVacationDays(user.getRemainingVacationDays()-daysRequested);
        userRepository.save(user);
        return vacationRequestRepository.save(request);
    }

    public List<VacationRequest> getUserRequests(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return vacationRequestRepository.findByAuthor(user);
    }

    public List<VacationRequest> getUserRequests(String username, String status, int limit, int offset) {
        PageRequest pageRequest = PageRequest.of(offset / limit, limit);
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return vacationRequestRepository.findByAuthorAndStatus(user, status, pageRequest).stream().toList();
    }


    public List<VacationRequest> getRequestByAuthorAndStatus(String username, VacationRequest.Status status) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return vacationRequestRepository.findByAuthorAndStatus(user, status);
    }

    public List<VacationRequest> getAllRequests() {
        return vacationRequestRepository.findAll();
    }

    public List<VacationRequest> getRequestsByStatus(VacationRequest.Status status) {
        return vacationRequestRepository.findByStatus(status);
    }

    @Transactional
    public VacationRequest processRequest(Long requestId, String managerUsername, boolean approved) {
        VacationRequest request = vacationRequestRepository.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Request not found"));

        User manager = userRepository.findByUsername(managerUsername)
                .orElseThrow(() -> new UsernameNotFoundException("Manager not found"));

        if (request.getStatus() != VacationRequest.Status.PENDING) {
            throw new RuntimeException("Request has already been processed");
        }

        request.setStatus(approved ? VacationRequest.Status.APPROVED : VacationRequest.Status.REJECTED);
        request.setResolvedBy(manager);
        request.setResolvedAt(LocalDateTime.now());

        if (approved) {
            User employee = request.getAuthor();
            int daysRequested = (int) Duration.between(
                    request.getVacationStartDate(),
                    request.getVacationEndDate()
            ).toDays() + 1;
            employee.setRemainingVacationDays(employee.getRemainingVacationDays() - daysRequested);
            userRepository.save(employee);
        }

        return vacationRequestRepository.save(request);
    }

    public List<VacationRequest> getOverlappingRequests() {
        return vacationRequestRepository.findAllOverlappingRequests();
    }

    public void deleteRequestById(String username, Long id) {
        List<VacationRequest> listRequest = vacationRequestRepository.findAll();
        VacationRequest request = vacationRequestRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Vacation request not found"));

        if (!request.getAuthor().getUsername().equals(username)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not authorized to delete this request");
        }
        int daysRequested = (int) Duration.between(request.getVacationStartDate(), request.getVacationEndDate()).toDays() + 1;

        userRepository.findById(request.getAuthor().getId()).ifPresent( requestor -> {
            requestor.setRemainingVacationDays(requestor.getRemainingVacationDays() + daysRequested);
        });

        vacationRequestRepository.delete(request);
    }

    public int getRemainingVacationDays(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return user.getRemainingVacationDays();
    }
}
