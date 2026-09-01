package com.MBEMNOVA.PULSEHR.controller;

import com.MBEMNOVA.PULSEHR.dto.EmployeAfficherDTO;
import com.MBEMNOVA.PULSEHR.dto.EmployeCreerDTO;
import com.MBEMNOVA.PULSEHR.service.EmployeService;
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
@RequestMapping("/api/employes")
@RequiredArgsConstructor
@Tag(name = "Employés", description = "Gestion des employés")
public class EmployeRestController {

    private final EmployeService employeService;

    @GetMapping
    @Operation(summary = "Liste des employés")
    public List<EmployeAfficherDTO> getAll() {
        return employeService.getAll(null, null);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Détail d'un employé")
    @ApiResponse(responseCode = "404", description = "Employé introuvable")
    public ResponseEntity<EmployeAfficherDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(employeService.getById(id));
    }

    @PostMapping
    @Operation(summary = "Créer un employé")
    @ApiResponse(responseCode = "201", description = "Employé créé")
    @ApiResponse(responseCode = "400", description = "Données invalides ou email en doublon")
    public ResponseEntity<EmployeAfficherDTO> create(@Valid @RequestBody EmployeCreerDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(employeService.save(dto));
    }
}