package com.MBEMNOVA.PULSEHR.scheduler;

import com.MBEMNOVA.PULSEHR.dto.StatistiquesDTO;
import com.MBEMNOVA.PULSEHR.entity.Contrat;
import com.MBEMNOVA.PULSEHR.entity.StatutContrat;
import com.MBEMNOVA.PULSEHR.repository.ContratRepository;
import com.MBEMNOVA.PULSEHR.repository.EvaluationRepository;
import com.MBEMNOVA.PULSEHR.service.StatistiqueService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

/**
 * RECHERCHE CONCEPTS:
 * Configured avec des expressions Spring Scheduled Read à partir des fichiers properties du profil.
 * Les cron dynamiques `${pulsehr.cron...}` s'adaptent selon que l'on est en profil Dev ou Prod.
 * Source: Documentation officielles Spring Task Execution and Scheduling.
 */
@Component
@RequiredArgsConstructor
public class ScheduledTasks {

    private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);

    private final ContratRepository contratRepository;
    private final EvaluationRepository evaluationRepository;
    private final StatistiqueService statistiqueService;

    @Scheduled(cron = "${pulsehr.cron.verification-contrats}")
    @Transactional
    public void verifierContratsExpires() {
        List<Contrat> aExpirer = contratRepository.findContratsExpiresANettoyer(LocalDate.now());
        aExpirer.forEach(c -> c.setStatut(StatutContrat.EXPIRE));
        contratRepository.saveAll(aExpirer);
        log.info("[SCHEDULED] Vérification des contrats : {} contrat(s) passé(s) en EXPIRE.", aExpirer.size());
    }

    @Scheduled(cron = "${pulsehr.cron.rapport-periodic}")
    public void genererRapportPeriodicConsole() {
        StatistiquesDTO stats = statistiqueService.getStatistiquesGlobales();
        long contratsExpirant7j = contratRepository.findContratsExpirantAvant(LocalDate.now().plusDays(7)).size();

        log.info("=== RAPPORT PERIODIQUE PULSEHR ===");
        log.info("Employés actifs: {}", stats.getNombreEmployesActifs());
        log.info("Contrats expirant dans 7 jours: {}", contratsExpirant7j);
        log.info("Masse salariale totale: {} FCFA", stats.getMasseSalarialeTotale());
        log.info("==================================");
    }

    @Scheduled(cron = "${pulsehr.cron.nettoyage-evaluations}")
    public void nettoyerEvaluationsIncompletes() {
        int deleted = evaluationRepository.deleteIncompleteEvaluations();
        log.info("[SCHEDULED] Nettoyage : {} évaluation(s) incomplète(s) supprimée(s).", deleted);
    }
}