package com.project.repositories;

import com.project.entities.RankingRow;
import io.micrometer.common.lang.NonNull;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface RankingRepository extends JpaRepository<RankingRow, UUID> {
    List<RankingRow> findAllByOrderByPointsDescGoalDifferenceDescGoalForCountDesc();
    List<RankingRow> findAllByOrderByRankAsc();
    @EntityGraph(attributePaths = {"team"})
    @NonNull
    Optional<RankingRow> findById(@NonNull UUID teamId);
}
