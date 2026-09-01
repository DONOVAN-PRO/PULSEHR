package com.MBEMNOVA.PULSEHR.controller;

import com.MBEMNOVA.PULSEHR.dto.DepartementAfficherDTO;
import com.MBEMNOVA.PULSEHR.dto.DepartementCreerDTO;
import com.MBEMNOVA.PULSEHR.service.DepartementService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/departements")
@RequiredArgsConstructor
@Tag(name = "Départements", description = "Gestion des départements de l'entreprise")
public class DepartementRestController {

    private final DepartementService departementService;

    @GetMapping
    @Operation(summary = "Obtenir la liste des départements")
    public List<DepartementAfficherDTO> getAll() {
        return departementService.getAll();
    }

    @PostMapping
    @Operation(summary = "Créer un département")
    @ApiResponse(responseCode = "201", description = "Département créé avec succès")
    @ApiResponse(responseCode = "400", description = "Données invalides")
    public ResponseEntity<DepartementAfficherDTO> create(@Valid @RequestBody DepartementCreerDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(departementService.save(dto));
    }
}