package com.example.vacation_service.repository;

import com.example.vacation_service.model.VacationRequest;
import com.example.vacation_service.model.RequestStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDateTime;
import java.util.List;

public interface VacationRequestRepository extends JpaRepository<VacationRequest, Long> {
    
    List<VacationRequest> findByStatus(RequestStatus status);
    
    List<VacationRequest> findByAuthor(String author);
    
    @Query("SELECT v FROM VacationRequest v WHERE " +
           "(:startDate BETWEEN v.vacationStartDate AND v.vacationEndDate) OR " +
           "(:endDate BETWEEN v.vacationStartDate AND v.vacationEndDate) OR " +
           "(v.vacationStartDate BETWEEN :startDate AND :endDate)")
    List<VacationRequest> findOverlappingRequests(
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate);
}
