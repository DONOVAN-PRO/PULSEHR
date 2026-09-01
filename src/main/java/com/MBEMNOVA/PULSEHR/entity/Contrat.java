package com.MBEMNOVA.PULSEHR.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "contrats")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Contrat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employe_id")
    private Employe employe;

    @Enumerated(EnumType.STRING)
    private TypeContrat typeContrat;

    @Enumerated(EnumType.STRING)
    private StatutContrat statut;

    private LocalDate dateDebut;
    private LocalDate dateFin;
    private BigDecimal salaireContrat;
}