package com.MBEMNOVA.PULSEHR.dto;

import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class DepartementCreerDTO {
    private Long id;

    @NotBlank(message = "Le nom ne peut pas être vide")
    @Size(min = 2, max = 100, message = "Le nom doit comporter entre 2 et 100 caractères")
    private String nom;

    @NotNull(message = "Le budget annuel est requis")
    @Positive(message = "Le budget doit être supérieur à zéro")
    private BigDecimal budgetAnnuel;

    @NotBlank(message = "La localisation est requise")
    private String localisation;

    private LocalDate dateCreation;
}