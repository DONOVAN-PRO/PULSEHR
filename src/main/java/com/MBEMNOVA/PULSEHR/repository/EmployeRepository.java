package com.MBEMNOVA.PULSEHR.repository;

import com.MBEMNOVA.PULSEHR.entity.Employe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeRepository extends JpaRepository<Employe, Long> {
    boolean existsByEmail(String email);

    @Query("SELECT e FROM Employe e WHERE e.departement.id = :deptId")
    List<Employe> findByDepartementId(Long deptId);

    @Query("SELECT e FROM Employe e WHERE LOWER(e.nom) LIKE LOWER(CONCAT('%', :nom, '%'))")
    List<Employe> searchByNom(String nom);

    @Query("SELECT DISTINCT e FROM Employe e JOIN e.contrats c WHERE c.statut = 'ACTIF'")
    List<Employe> findEmployesActifs();
}