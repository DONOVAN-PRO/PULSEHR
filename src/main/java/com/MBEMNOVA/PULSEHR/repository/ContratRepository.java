package com.MBEMNOVA.PULSEHR.repository;

import com.MBEMNOVA.PULSEHR.entity.Contrat;
import com.MBEMNOVA.PULSEHR.entity.StatutContrat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ContratRepository extends JpaRepository<Contrat, Long> {
    List<Contrat> findByStatut(StatutContrat statut);

    @Query("SELECT c FROM Contrat c WHERE c.statut = 'ACTIF' AND c.dateFin IS NOT NULL AND c.dateFin <= :limiteDate")
    List<Contrat> findContratsExpirantAvant(@Param("limiteDate") LocalDate limiteDate);

    @Query("SELECT c FROM Contrat c WHERE c.statut != 'EXPIRE' AND c.dateFin < :date")
    List<Contrat> findContratsExpiresANettoyer(@Param("date") LocalDate date);
}