package com.MBEMNOVA.PULSEHR.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class DepartementAfficherDTO {
    private Long id;
    private String nom;
    private BigDecimal budgetAnnuel;
    private String localisation;
    private LocalDate dateCreation;
    private int nombreEmployes;
}