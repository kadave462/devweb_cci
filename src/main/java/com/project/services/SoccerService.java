package com.project.services;

import com.project.dto.RankingRowDTO;
import com.project.dto.TeamDTO;

import java.util.List;
import java.util.UUID;

public interface SoccerService {
    List<RankingRowDTO> getRanking();

    default RankingRowDTO getRankingRow(UUID teamId) {
        return new RankingRowDTO(
                new TeamDTO(teamId, "Marseille"),
                3, 38, 22, 10, 6, 111, 92, 19, 72);
    }


}