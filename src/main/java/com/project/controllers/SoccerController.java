package com.project.controllers;

import com.project.dto.RankingRowDTO;
import com.project.services.DataSoccerService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;

import java.util.List;

@Controller
public class SoccerController {
    private final DataSoccerService soccerService;

    public SoccerController(DataSoccerService soccerService) {
        this.soccerService = soccerService;
    }

    @GetMapping("/")
    public String ranking(Model model) {
        List<RankingRowDTO> ranking = soccerService.getRanking();
        System.out.println("Ranking size: " + ranking.size());  // Log the size
        model.addAttribute("ranking", ranking);
        return "ranking";
    }

}

