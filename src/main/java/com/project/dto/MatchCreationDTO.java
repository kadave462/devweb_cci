package com.project.dtos;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

public record MatchCreationDTO(
        UUID id,
        UUID homeTeamId,
        UUID awayTeamId,
        Integer homeTeamGoals,
        Integer awayTeamGoals,
        LocalDate date,
        LocalTime time
) { }