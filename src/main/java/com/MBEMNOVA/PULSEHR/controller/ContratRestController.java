package com.MBEMNOVA.PULSEHR.controller;

import com.MBEMNOVA.PULSEHR.dto.ContratDTO;
import com.MBEMNOVA.PULSEHR.service.ContratService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @PostMapping
    @Operation(summary = "Créer un nouveau contrat")
    public ResponseEntity<ContratDTO> creerContrat(@RequestBody ContratDTO contratDTO) {
        ContratDTO nouveauContrat = contratService.creerContrat(contratDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(nouveauContrat);
    }

    @PostMapping("/{id}/signer")
    @Operation(summary = "Signer un contrat existant")
    public ResponseEntity<ContratDTO> signerContrat(@PathVariable Long id) {
        ContratDTO contratSigne = contratService.signerContrat(id);
        return ResponseEntity.ok(contratSigne);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Modifier un contrat")
    public ResponseEntity<ContratDTO> modifierContrat(@PathVariable Long id, @RequestBody ContratDTO contratDTO) {
        ContratDTO contratMisAJour = contratService.modifierContrat(id, contratDTO);
        return ResponseEntity.ok(contratMisAJour);
    }
}