package com.MBEMNOVA.PULSEHR.service.impl;

import com.MBEMNOVA.PULSEHR.dto.ContratDTO;
import com.MBEMNOVA.PULSEHR.entity.Contrat;
import com.MBEMNOVA.PULSEHR.entity.Employe;
import com.MBEMNOVA.PULSEHR.entity.StatutContrat;
import com.MBEMNOVA.PULSEHR.entity.TypeContrat;
import com.MBEMNOVA.PULSEHR.exception.EntityNotFoundException;
import com.MBEMNOVA.PULSEHR.repository.ContratRepository;
import com.MBEMNOVA.PULSEHR.repository.EmployeRepository;
import com.MBEMNOVA.PULSEHR.service.ContratService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ContratServiceImpl implements ContratService {

    private final ContratRepository contratRepository;
    private final EmployeRepository employeRepository; // <-- Injection obligatoire pour rattacher l'employé

    @Override
    @Transactional(readOnly = true)
    public List<ContratDTO> getContratsActifs() {
        return contratRepository.findByActifTrue()
                .stream()
                .map(this::toDto)
                .toList();
    }

    @Override
    public ContratDTO creerContrat(ContratDTO contratDTO) {
        Contrat contrat = toEntity(contratDTO);
        contrat.setEstSigne(false);
        contrat.setActif(true);
        Contrat contratEnregistre = contratRepository.save(contrat);
        return toDto(contratEnregistre);
    }

    @Override
    public ContratDTO signerContrat(Long id) {
        Contrat contrat = contratRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Contrat introuvable avec l'ID : " + id));

        contrat.setEstSigne(true);
        contrat.setDateSignature(LocalDate.now());

        Contrat contratMisAJour = contratRepository.save(contrat);
        return toDto(contratMisAJour);
    }

    @Override
    public ContratDTO modifierContrat(Long id, ContratDTO contratDTO) {
        Contrat contratExistant = contratRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Contrat introuvable avec l'ID : " + id));

        if (contratDTO.getTypeContrat() != null) {
            contratExistant.setTypeContrat(contratDTO.getTypeContrat());
        }

        if (contratDTO.getStatut() != null) {
            contratExistant.setStatut(contratDTO.getStatut());
        }

        if (contratDTO.getEmployeId() != null) {
            Employe employe = employeRepository.findById(contratDTO.getEmployeId())
                    .orElseThrow(() -> new EntityNotFoundException("Employé introuvable avec l'ID : " + contratDTO.getEmployeId()));
            contratExistant.setEmploye(employe);
        }

        contratExistant.setSalaire(contratDTO.getSalaire());
        contratExistant.setDateDebut(contratDTO.getDateDebut());
        contratExistant.setDateFin(contratDTO.getDateFin());

        Contrat contratMisAJour = contratRepository.save(contratExistant);
        return toDto(contratMisAJour);
    }

    // --- Conversions DTO <-> Entity ---

    private ContratDTO toDto(Contrat entity) {
        if (entity == null) return null;
        return ContratDTO.builder()
                .id(entity.getId())
                .typeContrat(entity.getTypeContrat())
                .statut(entity.getStatut())
                .salaire(entity.getSalaire())
                .dateDebut(entity.getDateDebut())
                .dateFin(entity.getDateFin())
                .dateSignature(entity.getDateSignature())
                .estSigne(entity.getEstSigne())
                .actif(entity.getActif())
                .employeId(entity.getEmploye() != null ? entity.getEmploye().getId() : null) // <-- Récupération de l'ID employé
                .build();
    }

    private Contrat toEntity(ContratDTO dto) {
        if (dto == null) return null;

        Employe employe = null;
        if (dto.getEmployeId() != null) {
            employe = employeRepository.findById(dto.getEmployeId())
                    .orElseThrow(() -> new EntityNotFoundException("Employé introuvable avec l'ID : " + dto.getEmployeId()));
        }

        return Contrat.builder()
                .id(dto.getId())
                .typeContrat(dto.getTypeContrat())
                .statut(dto.getStatut())
                .salaire(dto.getSalaire())
                .dateDebut(dto.getDateDebut())
                .dateFin(dto.getDateFin())
                .dateSignature(dto.getDateSignature())
                .estSigne(dto.getEstSigne() != null ? dto.getEstSigne() : false)
                .actif(dto.getActif() != null ? dto.getActif() : true)
                .employe(employe) // <-- Affectation de l'entité Employe
                .build();
    }
}