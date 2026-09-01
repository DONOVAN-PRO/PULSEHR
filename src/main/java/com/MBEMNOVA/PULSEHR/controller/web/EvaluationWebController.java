package com.MBEMNOVA.PULSEHR.controller.web;

import com.MBEMNOVA.PULSEHR.dto.EvaluationDTO;
import com.MBEMNOVA.PULSEHR.service.EmployeService;
import com.MBEMNOVA.PULSEHR.service.EvaluationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/evaluations")
@RequiredArgsConstructor
public class EvaluationWebController {

    private final EvaluationService evaluationService;
    private final EmployeService employeService;

    @GetMapping("/nouveau")
    public String formulaire(Model model) {
        model.addAttribute("evaluation", new EvaluationDTO());
        model.addAttribute("employes", employeService.getAll(null, null));
        return "evaluations/formulaire";
    }

    @PostMapping
    public String save(@Valid @ModelAttribute("evaluation") EvaluationDTO dto,
                       BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("employes", employeService.getAll(null, null));
            return "evaluations/formulaire";
        }
        evaluationService.save(dto);
        return "redirect:/";
    }
}