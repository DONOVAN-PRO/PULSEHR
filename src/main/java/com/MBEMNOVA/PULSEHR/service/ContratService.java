package com.MBEMNOVA.PULSEHR.service;

import com.MBEMNOVA.PULSEHR.dto.ContratDTO;
import com.MBEMNOVA.PULSEHR.entity.*;
import com.MBEMNOVA.PULSEHR.exception.EntityNotFoundException;
import com.MBEMNOVA.PULSEHR.repository.ContratRepository;
import com.MBEMNOVA.PULSEHR.repository.EmployeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ContratService {

    private final ContratRepository contratRepository;
    private final EmployeRepository employeRepository;

    @Transactional
    public List<ContratDTO> getContratsActifs() {
        List<Contrat> contrats = contratRepository.findByStatut(StatutContrat.ACTIF);

        // Exigence: Marquer automatiquement EXPIRE si dateFin depassee lors du chargement
        contrats.forEach(c -> {
            if (c.getDateFin() != null && c.getDateFin().isBefore(LocalDate.now())) {
                c.setStatut(StatutContrat.EXPIRE);
            }
        });

        return contrats.stream()
                .filter(c -> c.getStatut() == StatutContrat.ACTIF)
                .map(this::toDTO)
                .toList();
    }

    @Transactional
    public ContratDTO save(ContratDTO dto) {
        Employe emp = employeRepository.findById(dto.getEmployeId())
                .orElseThrow(() -> new EntityNotFoundException("Employé introuvable"));

        Contrat c = Contrat.builder()
                .id(dto.getId())
                .employe(emp)
                .typeContrat(dto.getTypeContrat())
                .statut(dto.getStatut() != null ? dto.getStatut() : StatutContrat.ACTIF)
                .dateDebut(dto.getDateDebut())
                .dateFin(dto.getDateFin())
                .salaireContrat(dto.getSalaireContrat())
                .build();

        return toDTO(contratRepository.save(c));
    }

    public ContratDTO toDTO(Contrat c) {
        return ContratDTO.builder()
                .id(c.getId())
                .employeId(c.getEmploye().getId())
                .nomEmploye(c.getEmploye().getPrenom() + " " + c.getEmploye().getNom())
                .typeContrat(c.getTypeContrat())
                .statut(c.getStatut())
                .dateDebut(c.getDateDebut())
                .dateFin(c.getDateFin())
                .salaireContrat(c.getSalaireContrat())
                .build();
    }
}