package com.project.services;

import com.project.dto.MatchDTO;
import com.project.dto.RankingRowDTO;
import com.project.dto.TeamDTO;
import com.project.entities.Match;
import com.project.entities.RankingRow;
import com.project.entities.Team;
import com.project.repositories.MatchRepository;
import com.project.repositories.RankingRepository;
import com.project.repositories.TeamRepository;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class JpaSoccerService implements SoccerService {
    private final DataSoccerService dataSoccerService;
    private final TeamRepository teamRepository;
    private final MatchRepository matchRepository;
    private final RankingRepository rankingRepository;

    public JpaSoccerService(DataSoccerService dataSoccerService,
                            TeamRepository teamRepository,
                            MatchRepository matchRepository,
                            RankingRepository rankingRepository) {
        this.dataSoccerService = dataSoccerService;
        this.teamRepository = teamRepository;
        this.matchRepository = matchRepository;
        this.rankingRepository = rankingRepository;
    }



    @EventListener
    public void handleContextRefresh(ContextRefreshedEvent event) {
        fillDatabase();
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void fillDatabase() {
        if (!getRanking().isEmpty()) { return; }
        for (TeamDTO team : dataSoccerService.getTeams()) addTeam(team);

        for (MatchDTO match : dataSoccerService.getMatches()) {
            addMatch(new com.project.dto.MatchCreationDTO(
                    match.id(),
                    match.homeTeam().id(), match.awayTeam().id(),
                    match.homeTeamGoals(), match.awayTeamGoals(),
                    match.date(), match.time()));
        }
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void addMatch(com.project.dto.MatchCreationDTO match) {
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
        updateRankingRow(match.homeTeamId(), match.homeTeamGoals(), match.awayTeamGoals());
        updateRankingRow(match.awayTeamId(), match.awayTeamGoals(), match.homeTeamGoals());
    }


    @Override
    @Transactional
    public void addTeam(TeamDTO team) {
        UUID teamId = team.id() == null ? UUID.randomUUID() : team.id();
        Team entity = new Team(teamId, team.name());
        teamRepository.save(entity);
        addEmptyRankingRow(entity);
        updateRanks();
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


    @Override
    public List<RankingRowDTO> getRanking() {
        return rankingRepository.findAllByOrderByRankAsc()
                .stream()
                .map(JpaSoccerService::toDTO)
                .toList();
    }

    @Override
    public RankingRowDTO getRankingRow(UUID teamId) {
        // This method will retrieve the ranking data for a specific team
        return null;
    }




    //private methods


    private static TeamDTO toDTO(Team team) {
        return new TeamDTO(team.id, team.name);
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

    private void addEmptyRankingRow(Team team) {
        RankingRow entity = new RankingRow(team, 0, 0, 0, 0, 0, 0, 0, 0, 0);
        rankingRepository.save(entity);
    }

    private void updateRankingRow(UUID teamId, int goalsForCount, int goalsAgainstCount) {
        RankingRow entity = rankingRepository.findById(teamId).orElseThrow();
        boolean win = goalsForCount > goalsAgainstCount;
        boolean draw = goalsForCount == goalsAgainstCount;
        boolean loss = goalsForCount < goalsAgainstCount;
        entity.matchPlayedCount++;
        entity.matchWonCount += win ? 1 : 0;
        entity.drawCount += draw ? 1 : 0;
        entity.matchLostCount += loss ? 1 : 0;
        entity.goalForCount += goalsForCount;
        entity.goalAgainstCount += goalsAgainstCount;
        entity.goalDifference += goalsForCount - goalsAgainstCount;
        entity.points += win ? 3 : draw ? 1 : 0;
        rankingRepository.save(entity);
    }

    private void updateRanks() {
        int rank = 1;
        for (RankingRow row : rankingRepository.findAllByOrderByPointsDescGoalDifferenceDescGoalForCountDesc()) {
            row.rank = rank;
            rankingRepository.save(row);
            rank++;
        }
    }

    private static RankingRowDTO toDTO(RankingRow rankingRow) {
        return new RankingRowDTO(
                toDTO(rankingRow.team),
                rankingRow.rank,
                rankingRow.matchPlayedCount,
                rankingRow.matchWonCount,
                rankingRow.matchLostCount,
                rankingRow.drawCount,
                rankingRow.goalForCount,
                rankingRow.goalAgainstCount,
                rankingRow.goalDifference,
                rankingRow.points
        );
    }




}