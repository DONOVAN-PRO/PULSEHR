package com.MBEMNOVA.PULSEHR.service.impl;

import com.MBEMNOVA.PULSEHR.dto.ContratDTO;
import com.MBEMNOVA.PULSEHR.entity.Contrat;
import com.MBEMNOVA.PULSEHR.repository.ContratRepository;
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

    @Override
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
                .orElseThrow(() -> new RuntimeException("Contrat introuvable avec l'ID : " + id));

        contrat.setEstSigne(true);
        contrat.setDateSignature(LocalDate.now());

        Contrat contratMisAJour = contratRepository.save(contrat);
        return toDto(contratMisAJour);
    }

    @Override
    public ContratDTO modifierContrat(Long id, ContratDTO contratDTO) {
        Contrat contratExistant = contratRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Contrat introuvable avec l'ID : " + id));

        contratExistant.setTypeContrat(contratDTO.getTypeContrat());
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
                .salaire(entity.getSalaire())
                .dateDebut(entity.getDateDebut())
                .dateFin(entity.getDateFin())
                .dateSignature(entity.getDateSignature())
                .estSigne(entity.getEstSigne())
                .actif(entity.getActif())
                .build();
    }

    private Contrat toEntity(ContratDTO dto) {
        if (dto == null) return null;
        return Contrat.builder()
                .id(dto.getId())
                .typeContrat(dto.getTypeContrat())
                .salaire(dto.getSalaire())
                .dateDebut(dto.getDateDebut())
                .dateFin(dto.getDateFin())
                .dateSignature(dto.getDateSignature())
                .estSigne(dto.getEstSigne() != null ? dto.getEstSigne() : false)
                .actif(dto.getActif() != null ? dto.getActif() : true)
                .build();
    }
}