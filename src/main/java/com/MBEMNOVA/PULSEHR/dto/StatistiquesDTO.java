package com.MBEMNOVA.PULSEHR.dto;

import lombok.*;

import java.math.BigDecimal;
import java.util.Map;
import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class StatistiquesDTO {
    private long nombreEmployesActifs;
    private BigDecimal masseSalarialeTotale;
    private String departementPlusGrand;
    private double tauxContratsExpirant60Jours;
    private double scoreMoyenEntreprise;
    private Map<String, Long> repartitionParTypeContrat;
    private List<TopEmployeDTO> top3Employes;

    @Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
    public static class TopEmployeDTO {
        private String nom;
        private String prenom;
        private Double scoreMoyen;
        private String departement;
    }
}