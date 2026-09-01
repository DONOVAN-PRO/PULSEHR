package com.MBEMNOVA.PULSEHR.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "contrats")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Contrat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String typeContrat; // ex: CDI, CDD, Stage
    private Double salaire;
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private LocalDate dateSignature;

    private Boolean estSigne = false;
    private Boolean actif = true;
}