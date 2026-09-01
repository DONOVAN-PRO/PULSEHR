package com.MBEMNOVA.PULSEHR.validation;

import com.MBEMNOVA.PULSEHR.dto.ContratDTO;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class DateContratValidator implements ConstraintValidator<DateContratValide, ContratDTO> {
    @Override
    public boolean isValid(ContratDTO dto, ConstraintValidatorContext context) {
        if (dto.getDateDebut() == null || dto.getDateFin() == null) {
            return true;
        }
        return !dto.getDateFin().isBefore(dto.getDateDebut());
    }
}