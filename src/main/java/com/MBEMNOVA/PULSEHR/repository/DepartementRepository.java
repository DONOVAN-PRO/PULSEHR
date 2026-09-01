package com.MBEMNOVA.PULSEHR.repository;

import com.MBEMNOVA.PULSEHR.entity.Departement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DepartementRepository extends JpaRepository<Departement, Long> {

    @Query("SELECT d.nom FROM Departement d JOIN d.employes e GROUP BY d.id, d.nom ORDER BY COUNT(e) DESC LIMIT 1")
    Optional<String> findDepartementAvecPlusDEmployes();
}