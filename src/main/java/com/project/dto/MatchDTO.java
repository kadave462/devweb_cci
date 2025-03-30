package com.project.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

public record MatchDTO(
        UUID id,
        TeamDTO homeTeam,
        TeamDTO awayTeam,
        int homeTeamGoals,
        int awayTeamGoals,
        LocalDate date,
        LocalTime time
) {}