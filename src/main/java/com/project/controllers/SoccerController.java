package com.project.controllers;

import com.project.dto.RankingRowDTO;
import com.project.services.DataSoccerService;
import com.project.services.SoccerService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.UUID;

@Controller
public class SoccerController {
    private final SoccerService soccerService;

    public SoccerController(SoccerService soccerService) {
        this.soccerService = soccerService;
    }

    @GetMapping("/")
    public String ranking(Model model) {
        List<RankingRowDTO> ranking = soccerService.getRanking();
        System.out.println("Ranking size: " + ranking.size());  // Log the size
        model.addAttribute("ranking", ranking);
        return "ranking";
    }

    @GetMapping("/team/{teamId}")
    public String team(@PathVariable UUID teamId, Model model) {
        model.addAttribute("teamId", teamId);
        return "team";
    }

}

