package com.MBEMNOVA.PULSEHR.controller;

import com.MBEMNOVA.PULSEHR.dto.StatistiquesDTO;
import com.MBEMNOVA.PULSEHR.service.StatistiqueService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/statistiques")
@RequiredArgsConstructor
@Tag(name = "Statistiques", description = "Statistiques globales du tableau de bord")
public class StatistiqueRestController {

    private final StatistiqueService statistiqueService;

    @GetMapping
    @Operation(summary = "Récupérer toutes les statistiques globales")
    public StatistiquesDTO getStats() {
        return statistiqueService.getStatistiquesGlobales();
    }
}