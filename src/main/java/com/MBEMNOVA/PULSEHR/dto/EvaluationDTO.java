package com.MBEMNOVA.PULSEHR.dto;

import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class EvaluationDTO {
    private Long id;

    @NotNull(message = "L'employé est requis")
    private Long employeId;
    private String nomEmploye;

    @Min(value = 1, message = "Le score minimum est 1")
    @Max(value = 5, message = "Le score maximum est 5")
    private Integer score;

    @NotBlank(message = "La période est requise")
    private String periode;

    private String commentaire;
    private LocalDate dateEvaluation;
}