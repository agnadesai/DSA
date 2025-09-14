package com.example.demo.mapper;

import com.example.demo.dto.VacationRequestDTO;
import com.example.demo.model.VacationRequest;
import org.springframework.stereotype.Component;

@Component
public class VacationRequestMapper {
    
    public VacationRequestDTO toDTO(VacationRequest request) {
        VacationRequestDTO dto = new VacationRequestDTO();
        dto.setId(request.getId());
        dto.setAuthorUsername(request.getAuthor().getUsername());
        dto.setStatus(request.getStatus());
        if (request.getResolvedBy() != null) {
            dto.setResolvedByUsername(request.getResolvedBy().getUsername());
        }
        dto.setRequestCreatedAt(request.getRequestCreatedAt());
        dto.setVacationStartDate(request.getVacationStartDate());
        dto.setVacationEndDate(request.getVacationEndDate());
        dto.setComment(request.getComment());
        dto.setVacationDays(request.getVacationDays());
        return dto;
    }
}
