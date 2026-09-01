package com.MBEMNOVA.PULSEHR.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "departements")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Departement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nom;

    private BigDecimal budgetAnnuel;
    private String localisation;
    private LocalDate dateCreation;

    @OneToMany(mappedBy = "departement", cascade = CascadeType.ALL)
    @Builder.Default
    private List<Employe> employes = new ArrayList<>();
}