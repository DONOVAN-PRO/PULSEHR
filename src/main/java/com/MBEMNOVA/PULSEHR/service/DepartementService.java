package com.MBEMNOVA.PULSEHR.service;

import com.MBEMNOVA.PULSEHR.dto.*;
import com.MBEMNOVA.PULSEHR.entity.Departement;
import com.MBEMNOVA.PULSEHR.exception.EntityNotFoundException;
import com.MBEMNOVA.PULSEHR.repository.DepartementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DepartementService {

    private final DepartementRepository departementRepository;

    public List<DepartementAfficherDTO> getAll() {
        return departementRepository.findAll().stream().map(this::toAfficherDTO).toList();
    }

    public DepartementAfficherDTO getById(Long id) {
        return departementRepository.findById(id)
                .map(this::toAfficherDTO)
                .orElseThrow(() -> new EntityNotFoundException("Département introuvable ID: " + id));
    }

    @Transactional
    public DepartementAfficherDTO save(DepartementCreerDTO dto) {
        Departement d = dto.getId() != null ?
                departementRepository.findById(dto.getId()).orElse(new Departement()) : new Departement();

        d.setNom(dto.getNom());
        d.setBudgetAnnuel(dto.getBudgetAnnuel());
        d.setLocalisation(dto.getLocalisation());
        if (d.getDateCreation() == null) d.setDateCreation(LocalDate.now());

        Departement saved = departementRepository.save(d);
        return toAfficherDTO(saved);
    }

    @Transactional
    public void delete(Long id) {
        Departement d = departementRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Département non trouvé"));
        if (!d.getEmployes().isEmpty()) {
            throw new IllegalStateException("Impossible de supprimer un département contenant des employés.");
        }
        departementRepository.deleteById(id);
    }

    private DepartementAfficherDTO toAfficherDTO(Departement d) {
        return DepartementAfficherDTO.builder()
                .id(d.getId())
                .nom(d.getNom())
                .budgetAnnuel(d.getBudgetAnnuel())
                .localisation(d.getLocalisation())
                .dateCreation(d.getDateCreation())
                .nombreEmployes(d.getEmployes() != null ? d.getEmployes().size() : 0)
                .build();
    }
}