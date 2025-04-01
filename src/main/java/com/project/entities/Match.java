package com.project.entities;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "matches")
public class Match {
    @Id
    public UUID id;

    @NotNull
    @ManyToOne
    public Team homeTeam;

    @NotNull
    @ManyToOne
    public Team awayTeam;

    @NotNull
    public Integer homeTeamGoals;

    @NotNull
    public Integer awayTeamGoals;

    @NotNull
    public LocalDate date;

    @NotNull
    public  LocalTime time;

    public Match() { }

    public Match(UUID id, Team homeTeam, Team awayTeam,
                 Integer homeTeamGoals, Integer awayTeamGoals,
                 LocalDate date, LocalTime time) {
        this.id = id;
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.homeTeamGoals = homeTeamGoals;
        this.awayTeamGoals = awayTeamGoals;
        this.date = date;
        this.time = time;
    }
}