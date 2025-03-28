package com.project.services;

import com.project.dto.RankingRowDTO;
import java.util.List;

public interface SoccerService {
    List<RankingRowDTO> getRanking();
}