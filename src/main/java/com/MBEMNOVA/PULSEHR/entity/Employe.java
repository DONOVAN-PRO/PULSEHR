package com.MBEMNOVA.PULSEHR.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "employes")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Employe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;
    private String prenom;

    @Column(unique = true, nullable = false)
    private String email;

    @Enumerated(EnumType.STRING)
    private PosteType poste;

    private BigDecimal salaire;
    private LocalDate dateEmbauche;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "departement_id")
    private Departement departement;

    @OneToMany(mappedBy = "employe", cascade = CascadeType.ALL)
    @Builder.Default
    private List<Contrat> contrats = new ArrayList<>();

    @OneToMany(mappedBy = "employe", cascade = CascadeType.ALL)
    @Builder.Default
    private List<Evaluation> evaluations = new ArrayList<>();
}