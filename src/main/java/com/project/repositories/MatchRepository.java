package com.project.repositories;

import com.project.entities.Match;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.UUID;

@Repository
public interface MatchRepository extends JpaRepository<Match, UUID> {
    List<Match> findAllByHomeTeamIdOrAwayTeamIdOrderByDateAscTimeAsc(UUID homeTeamId, UUID awayTeamId);
}