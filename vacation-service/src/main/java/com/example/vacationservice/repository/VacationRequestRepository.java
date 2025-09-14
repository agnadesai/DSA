package com.example.vacationservice.repository;

import com.example.vacationservice.model.User;
import com.example.vacationservice.model.VacationRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.time.LocalDateTime;
import java.util.List;

public interface VacationRequestRepository extends JpaRepository<VacationRequest, Long> {
    List<VacationRequest> findByAuthor(User author);
    List<VacationRequest> findByAuthorAndStatus(User author, VacationRequest.Status status);
    List<VacationRequest> findByStatus(VacationRequest.Status status);
    
    @Query("SELECT v FROM VacationRequest v WHERE " +
           "((v.vacationStartDate BETWEEN ?1 AND ?2) OR " +
           "(v.vacationEndDate BETWEEN ?1 AND ?2) OR " +
           "(v.vacationStartDate <= ?1 AND v.vacationEndDate >= ?2))")
    List<VacationRequest> findOverlappingRequests(LocalDateTime start, LocalDateTime end);
}
