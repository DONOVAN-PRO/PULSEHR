package com.MBEMNOVA.PULSEHR.repository;

import com.MBEMNOVA.PULSEHR.entity.Contrat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContratRepository extends JpaRepository<Contrat, Long> {
    List<Contrat> findByActifTrue();
}