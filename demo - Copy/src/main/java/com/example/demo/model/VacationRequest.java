package com.example.demo.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Data
public class VacationRequest {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "author_id")
    private User author;
    
    @Enumerated(EnumType.STRING)
    private RequestStatus status = RequestStatus.PENDING;
    
    @ManyToOne
    @JoinColumn(name = "resolved_by_id")
    private User resolvedBy;
    
    private LocalDateTime requestCreatedAt = LocalDateTime.now();
    
    private LocalDateTime vacationStartDate;
    
    private LocalDateTime vacationEndDate;
    
    private String comment;
    
    public int getVacationDays() {
        return (int) java.time.temporal.ChronoUnit.DAYS.between(
            vacationStartDate.toLocalDate(), 
            vacationEndDate.toLocalDate()
        ) + 1;
    }
}
