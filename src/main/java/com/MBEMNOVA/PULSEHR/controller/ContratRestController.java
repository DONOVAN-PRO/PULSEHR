package com.MBEMNOVA.PULSEHR.controller;

import com.MBEMNOVA.PULSEHR.dto.ContratDTO;
import com.MBEMNOVA.PULSEHR.service.ContratService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/contrats")
@RequiredArgsConstructor
@Tag(name = "Contrats", description = "Gestion des contrats des employés")
public class ContratRestController {

    private final ContratService contratService;

    @GetMapping("/actifs")
    @Operation(summary = "Obtenir tous les contrats actifs")
    public List<ContratDTO> getActifs() {
        return contratService.getContratsActifs();
    }
}