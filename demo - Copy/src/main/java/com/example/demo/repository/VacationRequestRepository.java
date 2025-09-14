package com.example.demo.repository;

import com.example.demo.model.RequestStatus;
import com.example.demo.model.User;
import com.example.demo.model.VacationRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.time.LocalDateTime;
import java.util.List;

public interface VacationRequestRepository extends JpaRepository<VacationRequest, Long> {
    List<VacationRequest> findByAuthor(User author);
    List<VacationRequest> findByAuthorAndStatus(User author, RequestStatus status);
    
    @Query("SELECT v FROM VacationRequest v WHERE v.status = 'PENDING' OR v.status = 'APPROVED'")
    List<VacationRequest> findAllPendingAndApproved();
    
    @Query("SELECT v FROM VacationRequest v WHERE " +
           "((v.vacationStartDate BETWEEN ?1 AND ?2) OR " +
           "(v.vacationEndDate BETWEEN ?1 AND ?2)) AND " +
           "v.status = 'APPROVED'")
    List<VacationRequest> findOverlappingRequests(LocalDateTime start, LocalDateTime end);
}
