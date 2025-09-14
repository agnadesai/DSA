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
    
    @Column(nullable = false)
    private LocalDateTime vacationStartDate;
    
    @Column(nullable = false)
    private LocalDateTime vacationEndDate;
    
    @Column(nullable = false)
    private int vacationDays;
    
    @Column(nullable = false)
    private LocalDateTime requestCreatedAt = LocalDateTime.now();
    
    private LocalDateTime resolvedAt;
    
    @ManyToOne
    @JoinColumn(name = "resolved_by_id")
    private User resolvedBy;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RequestStatus status = RequestStatus.PENDING;
    
    private String comment;
    
    // Helper method to check if dates are valid
    public boolean hasValidDates() {
        return vacationStartDate != null && 
               vacationEndDate != null && 
               !vacationStartDate.isAfter(vacationEndDate) &&
               !vacationStartDate.isBefore(LocalDateTime.now());
    }
}
