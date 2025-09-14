package com.example.vacationservice.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class VacationRequestDto {
    @NotNull
    private LocalDateTime vacationStartDate;
    @NotNull
    private LocalDateTime vacationEndDate;
}
