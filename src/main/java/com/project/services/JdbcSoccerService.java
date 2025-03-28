package com.project.services;

import com.project.dto.RankingRowDTO;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class JdbcSoccerService {
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
        parameters.put("points", row.points());
        parameters.put("goal_for_count", row.goalForCount());
        parameters.put("match_played_count", row.matchPlayedCount());
        parameters.put("goal_difference", row.goalDifference());
        parameters.put("match_won_count", row.matchWonCount());
        parameters.put("match_lost_count", row.matchLostCount());
        parameters.put("draw_count", row.drawCount());
        parameters.put("goal_against_count", row.goalAgainstCount());





        simpleJdbcInsert.execute(parameters);
    }

    @EventListener
    public void handleContextRefresh(ContextRefreshedEvent event) {
        fillDatabase();
    }

    public void fillDatabase() {
        System.out.println("fillDatabase");
        List<RankingRowDTO> ranking = dataSoccerService.getRanking();

        for (RankingRowDTO row : ranking) {
            insertRankingRow(row);
        }
    }
}
