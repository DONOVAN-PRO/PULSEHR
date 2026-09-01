package com.MBEMNOVA.PULSEHR.repository;

import com.MBEMNOVA.PULSEHR.entity.Contrat;
import com.MBEMNOVA.PULSEHR.entity.StatutContrat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDate;
import java.util.List;

public interface ContratRepository extends JpaRepository<Contrat, Long> {

    // Recherche tous les contrats actifs (propriété boolean actif = true)
    List<Contrat> findByActifTrue();

    // Recherche par statut Enum
    List<Contrat> findByStatut(StatutContrat statut);

    // Recherche des contrats expirant avant une date donnée
    @Query("SELECT c FROM Contrat c WHERE c.dateFin < :dateLimit")
    List<Contrat> findContratsExpirantAvant(@Param("dateLimit") LocalDate dateLimit);

    // Recherche des contrats expirés à nettoyer
    @Query("SELECT c FROM Contrat c WHERE c.dateFin < :dateLimit AND c.statut = com.MBEMNOVA.PULSEHR.entity.StatutContrat.ACTIF")
    List<Contrat> findContratsExpiresANettoyer(@Param("dateLimit") LocalDate dateLimit);
}