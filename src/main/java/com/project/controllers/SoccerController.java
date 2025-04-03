package com.project.controllers;

import com.project.dto.MatchDTO;
import com.project.dto.RankingRowDTO;
import com.project.dto.TeamDTO;
import com.project.dto.TeamFormDTO;
import com.project.services.DataSoccerService;
import com.project.services.SoccerService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

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
        RankingRowDTO row = soccerService.getRankingRow(teamId);
        List<MatchDTO> matches = soccerService.getMatches(teamId);

        model.addAttribute("teamId", teamId);
        model.addAttribute("row", row);
        model.addAttribute("matches", matches);

        return "team";
    }



    @PostMapping("/admin/team/add")
    public String addTeam(@Valid TeamDTO teamDTO, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return showAddTeamForm(teamDTO, model);
        }
        System.out.println(teamDTO.toString());
        return "redirect:/";
    }

    @GetMapping("/admin/team/add")
    public String showAddTeamForm(TeamDTO teamDTO, Model model) {
        model.addAttribute("teamDTO", teamDTO);
        return "add-team";
    }


}

