package com.MBEMNOVA.PULSEHR.service;

import com.MBEMNOVA.PULSEHR.dto.*;
import com.MBEMNOVA.PULSEHR.entity.*;
// Importer l'enum explicitement si elle se trouve dans un sous-package dédié :
// import com.MBEMNOVA.PULSEHR.entity.enums.StatutContrat;
import com.MBEMNOVA.PULSEHR.exception.DuplicateEmailException;
import com.MBEMNOVA.PULSEHR.exception.EntityNotFoundException;
import com.MBEMNOVA.PULSEHR.repository.DepartementRepository;
import com.MBEMNOVA.PULSEHR.repository.EmployeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmployeService {

    private final EmployeRepository employeRepository;
    private final DepartementRepository departementRepository;

    public List<EmployeAfficherDTO> getAll(Long deptId, String nomSearch) {
        List<Employe> list;
        if (deptId != null) {
            list = employeRepository.findByDepartementId(deptId);
        } else if (nomSearch != null && !nomSearch.isBlank()) {
            list = employeRepository.searchByNom(nomSearch);
        } else {
            list = employeRepository.findAll();
        }
        return list.stream().map(this::toAfficherDTO).toList();
    }

    public EmployeAfficherDTO getById(Long id) {
        return employeRepository.findById(id)
                .map(this::toAfficherDTO)
                .orElseThrow(() -> new EntityNotFoundException("Employé introuvable"));
    }

    @Transactional
    public EmployeAfficherDTO save(EmployeCreerDTO dto) {
        if (dto.getId() == null && employeRepository.existsByEmail(dto.getEmail())) {
            throw new DuplicateEmailException("Un employé avec cet email existe déjà : " + dto.getEmail());
        }

        Departement dept = departementRepository.findById(dto.getDepartementId())
                .orElseThrow(() -> new EntityNotFoundException("Département inexistant"));

        Employe e = dto.getId() != null ?
                employeRepository.findById(dto.getId()).orElse(new Employe()) : new Employe();

        e.setNom(dto.getNom());
        e.setPrenom(dto.getPrenom());
        e.setEmail(dto.getEmail());
        e.setPoste(dto.getPoste());
        e.setSalaire(dto.getSalaire());
        e.setDateEmbauche(dto.getDateEmbauche());
        e.setDepartement(dept);

        return toAfficherDTO(employeRepository.save(e));
    }

    public EmployeAfficherDTO toAfficherDTO(Employe e) {
        // Sécurisation contre les listes de contrats nulles
        List<Contrat> contrats = Optional.ofNullable(e.getContrats()).orElse(Collections.emptyList());

        // Filtrage du contrat actif :
        // Adapter la condition selon le type de 'actif' (Boolean) ou 'statut' (Enum) dans Contrat
        String statutActif = contrats.stream()
                .filter(c -> Boolean.TRUE.equals(c.getActif())) // Utilise getActif() si le champ dans Contrat est un Boolean
                .map(c -> c.getTypeContrat() != null ? c.getTypeContrat() : "N/A") // Pas de .name() si typeContrat est déjà une String
                .findFirst()
                .orElse("AUCUN");

        return EmployeAfficherDTO.builder()
                .id(e.getId())
                .nom(e.getNom())
                .prenom(e.getPrenom())
                .email(e.getEmail())
                .poste(e.getPoste())
                .salaire(e.getSalaire())
                .dateEmbauche(e.getDateEmbauche())
                .nomDepartement(e.getDepartement() != null ? e.getDepartement().getNom() : "N/A")
                .statutContratActif(statutActif)
                .build();
    }
}