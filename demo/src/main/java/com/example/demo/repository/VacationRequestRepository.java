package com.example.demo.repository;

import com.example.demo.model.RequestStatus;
import com.example.demo.model.User;
import com.example.demo.model.VacationRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface VacationRequestRepository extends JpaRepository<VacationRequest, Long> {
    List<VacationRequest> findByAuthor(User author);
    
    List<VacationRequest> findByAuthorAndStatus(User author, RequestStatus status);
    
    @Query("SELECT v FROM VacationRequest v WHERE v.author = :author " +
           "AND v.status <> 'REJECTED' " +
           "AND ((v.vacationStartDate BETWEEN :start AND :end) " +
           "OR (v.vacationEndDate BETWEEN :start AND :end) " +
           "OR (:start BETWEEN v.vacationStartDate AND v.vacationEndDate))")
    List<VacationRequest> findByAuthorAndDateRange(
        @Param("author") User author,
        @Param("start") LocalDateTime start,
        @Param("end") LocalDateTime end
    );
    
    @Query("SELECT v FROM VacationRequest v WHERE v.status IN ('PENDING', 'APPROVED')")
    List<VacationRequest> findAllPendingAndApproved();
    
    @Query("SELECT v FROM VacationRequest v WHERE " +
           "v.status = 'APPROVED' AND " +
           "((v.vacationStartDate BETWEEN :start AND :end) " +
           "OR (v.vacationEndDate BETWEEN :start AND :end) " +
           "OR (:start BETWEEN v.vacationStartDate AND v.vacationEndDate))")
    List<VacationRequest> findByDateRange(
        @Param("start") LocalDateTime start,
        @Param("end") LocalDateTime end
    );
}
