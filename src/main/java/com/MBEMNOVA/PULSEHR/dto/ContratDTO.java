package com.MBEMNOVA.PULSEHR.dto;

import com.MBEMNOVA.PULSEHR.entity.StatutContrat;
import com.MBEMNOVA.PULSEHR.entity.TypeContrat;
import com.MBEMNOVA.PULSEHR.validation.DateContratValide;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@DateContratValide
public class ContratDTO {
    private Long id;

    @NotNull(message = "L'employé est requis")
    private Long employeId;
    private String nomEmploye;

    @NotNull(message = "Le type de contrat est requis")
    private TypeContrat typeContrat;

    private StatutContrat statut;

    @NotNull(message = "La date de début est requise")
    private LocalDate dateDebut;

    private LocalDate dateFin;

    @NotNull(message = "Le salaire contractuel est requis")
    private BigDecimal salaireContrat;
}