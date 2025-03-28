package com.project.dto;

import java.util.UUID;

public record RankingRowDTO(
        TeamDTO team,
        int rank,
        int matchPlayedCount,
        int matchWonCount,
        int matchLostCount,
        int drawCount,
        int goalForCount,
        int goalAgainstCount,
        int goalDifference,
        int points
) {}