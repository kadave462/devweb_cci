package com.project.dto;

import jakarta.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

public record MatchCreationDTO(
        UUID id,
        @NotNull
        UUID homeTeamId,
        @NotNull
        UUID awayTeamId,
        @NotNull
        @Min(0)
        @Max(50)
        Integer homeTeamGoals,
        @NotNull
        @Min(0)
        @Max(50)
        Integer awayTeamGoals,
        @NotNull
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        LocalDate date,
        @NotNull
        @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
        LocalTime time
) {
}