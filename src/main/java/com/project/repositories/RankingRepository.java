package com.project.repositories;

import com.project.entities.RankingRow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface RankingRepository extends JpaRepository<RankingRow, UUID> {
    List<RankingRow> findAllByOrderByPointsDescGoalDifferenceDescGoalForCountDesc();
    List<RankingRow> findAllByOrderByRankAsc();
}
