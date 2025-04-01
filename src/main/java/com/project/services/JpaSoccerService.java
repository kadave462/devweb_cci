package com.project.services;

import com.project.dto.MatchDTO;
import com.project.dto.RankingRowDTO;
import com.project.dto.TeamDTO;
import com.project.entities.Match;
import com.project.entities.Team;
import com.project.repositories.MatchRepository;
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
    private final MatchRepository matchRepository;

    public JpaSoccerService(DataSoccerService dataSoccerService,
                            TeamRepository teamRepository,
                            MatchRepository matchRepository) {
        this.dataSoccerService = dataSoccerService;
        this.teamRepository = teamRepository;
        this.matchRepository = matchRepository;
    }




    @EventListener
    public void handleContextRefresh(ContextRefreshedEvent event) {
        fillDatabase();
    }

    public void fillDatabase() {
        if (!getRanking().isEmpty()) { return; }
        for (TeamDTO team : dataSoccerService.getTeams()) addTeam(team);

        for (MatchDTO match : dataSoccerService.getMatches()) {
            addMatch(new com.project.dtos.MatchCreationDTO(
                    match.id(),
                    match.homeTeam().id(), match.awayTeam().id(),
                    match.homeTeamGoals(), match.awayTeamGoals(),
                    match.date(), match.time()));
        }
    }

    public void addMatch(com.project.dtos.MatchCreationDTO match) {
        Team homeTeam = teamRepository.findById(match.homeTeamId()).orElseThrow();
        Team awayTeam = teamRepository.findById(match.awayTeamId()).orElseThrow();

        Match entity = new Match(
                match.id(),
                homeTeam,
                awayTeam,
                match.homeTeamGoals(),
                match.awayTeamGoals(),
                match.date(),
                match.time()
        );
        matchRepository.save(entity);
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

    @Override
    public List<MatchDTO> getMatches(UUID teamId) {
        return matchRepository
                .findAllByHomeTeamIdOrAwayTeamIdOrderByDateAscTimeAsc(teamId, teamId)
                .stream()
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

    private static MatchDTO toDTO(Match match) {
        return new MatchDTO(
                match.id,
                toDTO(match.homeTeam),
                toDTO(match.awayTeam),
                match.homeTeamGoals,
                match.awayTeamGoals,
                match.date,
                match.time
        );
    }

}