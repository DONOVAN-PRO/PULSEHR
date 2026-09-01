package com.MBEMNOVA.PULSEHR.service;

import com.MBEMNOVA.PULSEHR.dto.StatistiquesDTO;
import com.MBEMNOVA.PULSEHR.entity.*;
import com.MBEMNOVA.PULSEHR.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

@Service
@RequiredArgsConstructor
public class StatistiqueService {

    private final EmployeRepository employeRepository;
    private final DepartementRepository departementRepository;
    private final ContratRepository contratRepository;
    private final EvaluationRepository evaluationRepository;

    public StatistiquesDTO getStatistiquesGlobales() {
        List<Employe> employesActifs = employeRepository.findEmployesActifs();

        long countActifs = employesActifs.size();

        BigDecimal masseSalariale = employesActifs.stream()
                .map(Employe::getSalaire)
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        String deptMax = departementRepository.findDepartementAvecPlusDEmployes()
                .orElse("Aucun");

        List<Contrat> contratsActifs = contratRepository.findByStatut(StatutContrat.ACTIF);
        long totalContratsActifs = contratsActifs.size();

        long expirant60J = contratRepository.findContratsExpirantAvant(LocalDate.now().plusDays(60)).size();
        double tauxExpirant = totalContratsActifs > 0 ? ((double) expirant60J / totalContratsActifs) * 100 : 0.0;

        Double scoreMoyen = evaluationRepository.findAverageScoreGlobalSince(LocalDate.now().minusMonths(12));

        Map<String, Long> repartition = new HashMap<>();
        for (TypeContrat tc : TypeContrat.values()) {
            long count = contratsActifs.stream().filter(c -> c.getTypeContrat() == tc).count();
            repartition.put(tc.name(), count);
        }

        List<Object[]> topRaw = evaluationRepository.findTopEmployesScores();
        List<StatistiquesDTO.TopEmployeDTO> top3 = new ArrayList<>();

        for (int i = 0; i < Math.min(3, topRaw.size()); i++) {
            Long empId = (Long) topRaw.get(i)[0];
            Double score = (Double) topRaw.get(i)[1];

            Employe emp = employeRepository.findById(empId).orElse(null);
            if (emp != null) {
                top3.add(StatistiquesDTO.TopEmployeDTO.builder()
                        .nom(emp.getNom())
                        .prenom(emp.getPrenom())
                        .scoreMoyen(score)
                        .departement(emp.getDepartement() != null ? emp.getDepartement().getNom() : "N/A")
                        .build());
            }
        }

        return StatistiquesDTO.builder()
                .nombreEmployesActifs(countActifs)
                .masseSalarialeTotale(masseSalariale)
                .departementPlusGrand(deptMax)
                .tauxContratsExpirant60Jours(Math.round(tauxExpirant * 100.0) / 100.0)
                .scoreMoyenEntreprise(scoreMoyen != null ? Math.round(scoreMoyen * 100.0) / 100.0 : 0.0)
                .repartitionParTypeContrat(repartition)
                .top3Employes(top3)
                .build();
    }
}