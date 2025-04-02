package com.project.entities;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "ranking")
public class RankingRow {
    @Id
    public UUID teamId;

    @OneToOne
    @MapsId
    public Team team;

    public int rank;
    public int matchPlayedCount;
    public int matchWonCount;
    public int matchLostCount;
    public int drawCount;
    public int goalForCount;
    public int goalAgainstCount;
    public int goalDifference;
    public int points;

    public RankingRow() {}

    public RankingRow(Team team, int rank, int matchPlayedCount, int matchWonCount,
                      int matchLostCount, int drawCount, int goalForCount,
                      int goalAgainstCount, int goalDifference, int points) {
        this.teamId = team.id;
        this.team = team;
        this.rank = rank;
        this.matchPlayedCount = matchPlayedCount;
        this.matchWonCount = matchWonCount;
        this.matchLostCount = matchLostCount;
        this.drawCount = drawCount;
        this.goalForCount = goalForCount;
        this.goalAgainstCount = goalAgainstCount;
        this.goalDifference = goalDifference;
        this.points = points;
    }
}