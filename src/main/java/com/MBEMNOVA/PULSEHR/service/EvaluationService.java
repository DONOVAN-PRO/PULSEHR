package com.MBEMNOVA.PULSEHR.service;

import com.MBEMNOVA.PULSEHR.dto.EvaluationDTO;
import com.MBEMNOVA.PULSEHR.entity.Evaluation;
import com.MBEMNOVA.PULSEHR.entity.Employe;
import com.MBEMNOVA.PULSEHR.exception.EntityNotFoundException;
import com.MBEMNOVA.PULSEHR.repository.EvaluationRepository;
import com.MBEMNOVA.PULSEHR.repository.EmployeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class EvaluationService {

    private final EvaluationRepository evaluationRepository;
    private final EmployeRepository employeRepository;

    @Transactional
    public EvaluationDTO save(EvaluationDTO dto) {
        Employe emp = employeRepository.findById(dto.getEmployeId())
                .orElseThrow(() -> new EntityNotFoundException("Employé introuvable"));

        Evaluation ev = Evaluation.builder()
                .employe(emp)
                .score(dto.getScore())
                .periode(dto.getPeriode())
                .commentaire(dto.getCommentaire())
                .dateEvaluation(LocalDate.now())
                .build();

        Evaluation saved = evaluationRepository.save(ev);
        dto.setId(saved.getId());
        dto.setDateEvaluation(saved.getDateEvaluation());
        return dto;
    }

    public Double getMoyenneScoreEmploye(Long employeId) {
        return evaluationRepository.findAverageScoreByEmployeId(employeId);
    }
}