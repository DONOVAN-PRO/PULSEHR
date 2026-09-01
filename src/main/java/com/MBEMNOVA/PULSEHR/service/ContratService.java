package com.MBEMNOVA.PULSEHR.service;

import com.MBEMNOVA.PULSEHR.dto.ContratDTO;
import java.util.List;

public interface ContratService {
    List<ContratDTO> getContratsActifs();
    ContratDTO creerContrat(ContratDTO contratDTO);
    ContratDTO signerContrat(Long id);
    ContratDTO modifierContrat(Long id, ContratDTO contratDTO);
}