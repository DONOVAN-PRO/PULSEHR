package com.MBEMNOVA.PULSEHR.dto;

import lombok.*;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContratDTO {

    private Long id;
    private String typeContrat;
    private Double salaire;
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private LocalDate dateSignature;
    private Boolean estSigne;
    private Boolean actif;
}