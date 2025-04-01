package com.project.services;

import com.project.dto.MatchDTO;
import com.project.dto.RankingRowDTO;
import com.project.dto.TeamDTO;
import com.project.entities.Team;
import com.project.repositories.TeamRepository;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class JpaSoccerService implements SoccerService {
    private final DataSoccerService dataSoccerService;
    private final TeamRepository teamRepository;

    public JpaSoccerService(DataSoccerService dataSoccerService, TeamRepository teamRepository) {
        this.dataSoccerService = dataSoccerService;
        this.teamRepository = teamRepository;
    }
    

    @EventListener
    public void handleContextRefresh(ContextRefreshedEvent event) {
        fillDatabase();
    }

    public void fillDatabase() {
        if (!getRanking().isEmpty()) { return; }
        for (TeamDTO team : dataSoccerService.getTeams()) addTeam(team);
    }

    public void addTeam(TeamDTO team) {
        Team entity = new Team(team.id(), team.name());
        teamRepository.save(entity);
    }

    @Override
    public List<TeamDTO> getTeams() {
        return teamRepository.findAll().stream()
                .map(JpaSoccerService::toDTO)
                .toList();
    }

    private static TeamDTO toDTO(Team team) {
        return new TeamDTO(team.id, team.name);
    }

    @Override
    public List<RankingRowDTO> getRanking() {
        return List.of();
    }

    @Override
    public RankingRowDTO getRankingRow(UUID teamId) {
        // This method will retrieve the ranking data for a specific team
        return null;
    }

    @Override
    public List<MatchDTO> getMatches(UUID teamId) {
        // This method will retrieve matches for a specific team
        return List.of();
    }
}