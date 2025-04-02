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

    @GetMapping("/admin/team/add")
    public String showAddTeamForm(Model model) {
        model.addAttribute("teamForm", new TeamFormDTO());
        return "add-team";
    }

    @PostMapping("/admin/team/add")
    public String addTeam(@Valid @ModelAttribute("teamForm") TeamFormDTO teamForm,
                          BindingResult result) {
        if (result.hasErrors()) {
            return "add-team";
        }

        // Generate a new UUID for the team
        UUID teamId = UUID.randomUUID();

        // Create a TeamDTO from the form data
        TeamDTO newTeam = new TeamDTO(teamId, teamForm.getName());

        // Add the team to the database
        soccerService.addTeam(newTeam);

        // Redirect to the ranking page
        return "redirect:/";
    }
}

