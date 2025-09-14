package com.example.demo.dto;

import com.example.demo.model.RequestStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class VacationRequestDTO {
    private Long id;
    private String authorUsername;
    private RequestStatus status;
    private String resolvedByUsername;
    private LocalDateTime requestCreatedAt;
    private LocalDateTime vacationStartDate;
    private LocalDateTime vacationEndDate;
    private String comment;
    private int vacationDays;
}
