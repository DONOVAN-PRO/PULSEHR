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

    @Enumerated(EnumType.STRING)
    private TypeContrat typeContrat;

    @Enumerated(EnumType.STRING)
    private StatutContrat statut;

    private Double salaire;
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private LocalDate dateSignature;

    @Builder.Default
    private Boolean estSigne = false;

    @Builder.Default
    private Boolean actif = true;

    // --- Champ manquant causant l'erreur ---
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employe_id")
    private Employe employe;
}