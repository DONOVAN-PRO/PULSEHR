package com.MBEMNOVA.PULSEHR.controller.web;

import com.MBEMNOVA.PULSEHR.service.StatistiqueService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class DashboardWebController {

    private final StatistiqueService statistiqueService;

    @GetMapping("/")
    public String dashboard(Model model) {
        model.addAttribute("stats", statistiqueService.getStatistiquesGlobales());
        return "dashboard";
    }
}