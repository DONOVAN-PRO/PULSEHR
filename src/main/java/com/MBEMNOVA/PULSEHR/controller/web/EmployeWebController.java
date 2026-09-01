package com.MBEMNOVA.PULSEHR.controller.web;

import com.MBEMNOVA.PULSEHR.dto.EmployeCreerDTO;
import com.MBEMNOVA.PULSEHR.entity.PosteType;
import com.MBEMNOVA.PULSEHR.exception.DuplicateEmailException;
import com.MBEMNOVA.PULSEHR.service.DepartementService;
import com.MBEMNOVA.PULSEHR.service.EmployeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/employes")
@RequiredArgsConstructor
public class EmployeWebController {

    private final EmployeService employeService;
    private final DepartementService departementService;

    @GetMapping
    public String list(@RequestParam(required = false) Long deptId,
                       @RequestParam(required = false) String nom,
                       Model model) {
        model.addAttribute("employes", employeService.getAll(deptId, nom));
        model.addAttribute("departements", departementService.getAll());
        return "employes/liste";
    }

    @GetMapping("/nouveau")
    public String nouveauForm(Model model) {
        model.addAttribute("employe", new EmployeCreerDTO());
        model.addAttribute("departements", departementService.getAll());
        model.addAttribute("postes", PosteType.values());
        return "employes/formulaire";
    }

    @PostMapping
    public String save(@Valid @ModelAttribute("employe") EmployeCreerDTO dto,
                       BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("departements", departementService.getAll());
            model.addAttribute("postes", PosteType.values());
            return "employes/formulaire";
        }
        try {
            employeService.save(dto);
        } catch (DuplicateEmailException ex) {
            result.rejectValue("email", "error.employe", ex.getMessage());
            model.addAttribute("departements", departementService.getAll());
            model.addAttribute("postes", PosteType.values());
            return "employes/formulaire";
        }
        return "redirect:/employes";
    }
}