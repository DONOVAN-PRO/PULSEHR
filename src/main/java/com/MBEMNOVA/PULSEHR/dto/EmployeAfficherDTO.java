package com.MBEMNOVA.PULSEHR.dto;

import com.MBEMNOVA.PULSEHR.entity.PosteType;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class EmployeAfficherDTO {
    private Long id;
    private String nom;
    private String prenom;
    private String email;
    private PosteType poste;
    private BigDecimal salaire;
    private LocalDate dateEmbauche;
    private String nomDepartement;
    private String statutContratActif;
}