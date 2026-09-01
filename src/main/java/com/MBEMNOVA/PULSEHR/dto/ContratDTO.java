package com.MBEMNOVA.PULSEHR.dto;

import com.MBEMNOVA.PULSEHR.entity.TypeContrat;
import com.MBEMNOVA.PULSEHR.entity.StatutContrat;
import lombok.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContratDTO {
    private Long id;
    private TypeContrat typeContrat;
    private StatutContrat statut;
    private Double salaire;
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private LocalDate dateSignature;
    private Boolean estSigne;
    private Boolean actif;

    // Champ essentiel pour la liaison HTTP API
    private Long employeId;
}