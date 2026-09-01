package com.MBEMNOVA.PULSEHR.controller.web;

import com.MBEMNOVA.PULSEHR.service.ContratService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
}