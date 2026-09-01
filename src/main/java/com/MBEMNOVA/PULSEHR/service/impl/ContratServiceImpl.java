package com.MBEMNOVA.PULSEHR.service.impl;

import com.MBEMNOVA.PULSEHR.dto.ContratDTO;
import com.MBEMNOVA.PULSEHR.entity.Contrat;
import com.MBEMNOVA.PULSEHR.entity.TypeContrat;
import com.MBEMNOVA.PULSEHR.exception.EntityNotFoundException;
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

        // Conversion String (DTO) -> Enum (Entity)
        if (contratDTO.getTypeContrat() != null) {
            contratExistant.setTypeContrat(TypeContrat.valueOf(contratDTO.getTypeContrat().toUpperCase()));
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
                // Conversion Enum (Entity) -> String (DTO)
                .typeContrat(entity.getTypeContrat() != null ? entity.getTypeContrat().name() : null)
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
                // Conversion String (DTO) -> Enum (Entity)
                .typeContrat(dto.getTypeContrat() != null ? TypeContrat.valueOf(dto.getTypeContrat().toUpperCase()) : null)
                .salaire(dto.getSalaire())
                .dateDebut(dto.getDateDebut())
                .dateFin(dto.getDateFin())
                .dateSignature(dto.getDateSignature())
                .estSigne(dto.getEstSigne() != null ? dto.getEstSigne() : false)
                .actif(dto.getActif() != null ? dto.getActif() : true)
                .build();
    }
}