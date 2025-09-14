package com.example.vacationservice.repository;

import com.example.vacationservice.model.User;
import com.example.vacationservice.model.VacationRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.time.LocalDateTime;
import java.util.List;

public interface VacationRequestRepository extends JpaRepository<VacationRequest, Long> {
    List<VacationRequest> findByAuthor(User author);
    Page<VacationRequest> findByAuthorAndStatus(User author, String Status, Pageable pageable);

    List<VacationRequest> findByAuthorAndStatus(User author, VacationRequest.Status status);
    List<VacationRequest> findByStatus(VacationRequest.Status status);

    @Query("SELECT v FROM VacationRequest v WHERE " +
           "((v.vacationStartDate BETWEEN ?1 AND ?2) OR " +
           "(v.vacationEndDate BETWEEN ?1 AND ?2) OR " +
           "(v.vacationStartDate <= ?1 AND v.vacationEndDate >= ?2))")
    List<VacationRequest> findOverlappingRequests(LocalDateTime start, LocalDateTime end);

    @Query("SELECT DISTINCT v1 FROM VacationRequest v1, VacationRequest v2 WHERE " +
           "v1.id != v2.id AND v1.status = 'PENDING' AND v2.status = 'PENDING' AND " +
           "((v1.vacationStartDate BETWEEN v2.vacationStartDate AND v2.vacationEndDate) OR " +
           "(v1.vacationEndDate BETWEEN v2.vacationStartDate AND v2.vacationEndDate) OR " +
           "(v1.vacationStartDate <= v2.vacationStartDate AND v1.vacationEndDate >= v2.vacationEndDate))")
    List<VacationRequest> findAllOverlappingRequests();
}
