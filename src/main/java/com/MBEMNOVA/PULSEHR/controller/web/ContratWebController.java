package com.MBEMNOVA.PULSEHR.controller.web;

import com.MBEMNOVA.PULSEHR.dto.ContratDTO;
import com.MBEMNOVA.PULSEHR.service.ContratService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/contrats")
@RequiredArgsConstructor
public class ContratWebController {

    private final ContratService contratService;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("contrats", contratService.getContratsActifs());
        return "contrats/liste";
    }

    @GetMapping("/nouveau")
    public String afficherFormulaire(Model model) {
        model.addAttribute("contrat", new ContratDTO());
        return "contrats/formulaire";
    }

    @PostMapping
    public String enregistrerContrat(@ModelAttribute("contrat") ContratDTO contratDTO) {
        contratService.creerContrat(contratDTO);
        return "redirect:/contrats";
    }

    @PostMapping("/{id}/signer")
    public String signerContrat(@PathVariable Long id) {
        contratService.signerContrat(id);
        return "redirect:/contrats";
    }
}