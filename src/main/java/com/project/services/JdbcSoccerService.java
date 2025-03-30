package com.project.services;

import com.project.dto.RankingRowDTO;
import com.project.dto.TeamDTO;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.Optional;

@Service
public class JdbcSoccerService implements SoccerService {
    private final DataSoccerService dataSoccerService;
    private final JdbcTemplate jdbcTemplate;

    public JdbcSoccerService(DataSoccerService dataSoccerService, JdbcTemplate jdbcTemplate) {
        this.dataSoccerService = dataSoccerService;
        this.jdbcTemplate = jdbcTemplate;
    }

    private void insertRankingRow(RankingRowDTO row) {
        SimpleJdbcInsert simpleJdbcInsert =
                new SimpleJdbcInsert(jdbcTemplate).withTableName("ranking");
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("team_id", row.team().id());
        parameters.put("team_name", row.team().name());
        parameters.put("rank", row.rank());
        parameters.put("match_played_count", row.matchPlayedCount());
        parameters.put("match_won_count", row.matchWonCount());
        parameters.put("match_lost_count", row.matchLostCount());
        parameters.put("draw_count", row.drawCount());
        parameters.put("goal_for_count", row.goalForCount());
        parameters.put("goal_against_count", row.goalAgainstCount());
        parameters.put("goal_difference", row.goalDifference());
        parameters.put("points", row.points());
        simpleJdbcInsert.execute(parameters);
    }

    @EventListener
    public void handleContextRefresh(ContextRefreshedEvent event) {
        fillDatabase();
    }

    public void fillDatabase() {
        System.out.println("fillDatabase");
        List<RankingRowDTO> ranking = dataSoccerService.getRanking();

        jdbcTemplate.execute("DELETE FROM ranking");

        for (RankingRowDTO row : ranking) {
            insertRankingRow(row);
        }
    }

    @Override
    public List<RankingRowDTO> getRanking() {
        String sql = "SELECT * FROM ranking ORDER BY rank";
        return jdbcTemplate.query(sql, rankingRowMapper());
    }

    public Optional<RankingRowDTO> getTeamRanking(UUID teamId) {
        String sql = "SELECT * FROM ranking WHERE team_id = ?";
        List<RankingRowDTO> results = jdbcTemplate.query(sql, rankingRowMapper(), teamId);
        return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
    }

    private RowMapper<RankingRowDTO> rankingRowMapper() {
        return (rs, rowNum) -> {
            UUID teamId = UUID.fromString(rs.getString("team_id"));
            String teamName = rs.getString("team_name");
            TeamDTO team = new TeamDTO(teamId, teamName);

            return new RankingRowDTO(
                    team,
                    rs.getInt("rank"),
                    rs.getInt("match_played_count"),
                    rs.getInt("match_won_count"),
                    rs.getInt("match_lost_count"),
                    rs.getInt("draw_count"),
                    rs.getInt("goal_for_count"),
                    rs.getInt("goal_against_count"),
                    rs.getInt("goal_difference"),
                    rs.getInt("points")
            );
        };
    }
}