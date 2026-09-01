package com.MBEMNOVA.PULSEHR.repository;

import com.MBEMNOVA.PULSEHR.entity.Evaluation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface EvaluationRepository extends JpaRepository<Evaluation, Long> {

    @Query("SELECT AVG(ev.score) FROM Evaluation ev WHERE ev.employe.id = :empId")
    Double findAverageScoreByEmployeId(@Param("empId") Long empId);

    @Query("SELECT AVG(ev.score) FROM Evaluation ev WHERE ev.dateEvaluation >= :dateLimite")
    Double findAverageScoreGlobalSince(@Param("dateLimite") LocalDate dateLimite);

    @Query("SELECT ev.employe.id, AVG(ev.score) as avgScore FROM Evaluation ev GROUP BY ev.employe.id ORDER BY avgScore DESC")
    List<Object[]> findTopEmployesScores();

    @Transactional
    @Modifying
    @Query("DELETE FROM Evaluation ev WHERE ev.score IS NULL AND (ev.commentaire IS NULL OR TRIM(ev.commentaire) = '')")
    int deleteIncompleteEvaluations();
}