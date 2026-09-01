package com.MBEMNOVA.PULSEHR.controller.web;

import com.MBEMNOVA.PULSEHR.dto.DepartementCreerDTO;
import com.MBEMNOVA.PULSEHR.service.DepartementService;
import com.MBEMNOVA.PULSEHR.service.EmployeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/departements")
@RequiredArgsConstructor
public class DepartementWebController {

    private final DepartementService departementService;
    private final EmployeService employeService;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("departements", departementService.getAll());
        return "departements/liste";
    }

    @GetMapping("/nouveau")
    public String nouveauForm(Model model) {
        model.addAttribute("departement", new DepartementCreerDTO());
        return "departements/formulaire";
    }

    @PostMapping
    public String save(@Valid @ModelAttribute("departement") DepartementCreerDTO dto,
                       BindingResult result) {
        if (result.hasErrors()) {
            return "departements/formulaire";
        }
        departementService.save(dto);
        return "redirect:/departements";
    }

    @GetMapping("/{id}")
    public String detail(@PathVariable Long id, Model model) {
        model.addAttribute("departement", departementService.getById(id));
        model.addAttribute("employes", employeService.getAll(id, null));
        return "departements/detail";
    }

    @PostMapping("/{id}/supprimer")
    public String delete(@PathVariable Long id, Model model) {
        try {
            departementService.delete(id);
        } catch (IllegalStateException ex) {
            model.addAttribute("errorMessage", ex.getMessage());
            model.addAttribute("departements", departementService.getAll());
            return "departements/liste";
        }
        return "redirect:/departements";
    }
}