package com.project.services;

import com.project.dto.RankingRowDTO;
import com.project.dto.TeamDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class JdbcSoccerServiceTest {

    @Autowired
    private JdbcSoccerService jdbcSoccerService;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @MockBean
    private DataSoccerService dataSoccerService;

    @Test
    public void testFillDatabase() {
        // Prepare mock data
        UUID teamId1 = UUID.randomUUID();
        UUID teamId2 = UUID.randomUUID();

        List<RankingRowDTO> mockRanking = List.of(
                new RankingRowDTO(
                        new TeamDTO(teamId1, "Test Team 1"),
                        1, 10, 7, 2, 1, 15, 6, 9, 22
                ),
                new RankingRowDTO(
                        new TeamDTO(teamId2, "Test Team 2"),
                        2, 10, 5, 3, 2, 12, 8, 4, 18
                )
        );

        // Configure the mock to return our test data
        when(dataSoccerService.getRanking()).thenReturn(mockRanking);

        // Call the method to test
        jdbcSoccerService.fillDatabase();

        // Verify the database was populated correctly
        assertEquals(2, jdbcTemplate.queryForObject("SELECT COUNT(*) FROM ranking", Integer.class));

        // Check first team data
        List<RankingRowDTO> teams = jdbcSoccerService.getRanking();
        assertEquals(2, teams.size());

        RankingRowDTO firstTeam = teams.get(0);
        assertEquals("Test Team 1", firstTeam.team().name());
        assertEquals(1, firstTeam.rank());
        assertEquals(10, firstTeam.matchPlayedCount());
        assertEquals(7, firstTeam.matchWonCount());
        assertEquals(22, firstTeam.points());

        // Test getTeamRanking method
        Optional<RankingRowDTO> team = jdbcSoccerService.getTeamRanking(teamId1);
        assertTrue(team.isPresent());
        assertEquals("Test Team 1", team.get().team().name());

        // Test non-existent team
        Optional<RankingRowDTO> nonExistentTeam = jdbcSoccerService.getTeamRanking(UUID.randomUUID());
        assertFalse(nonExistentTeam.isPresent());
    }

    @Test
    public void testGetRanking() {
        // Prepare mock data
        UUID teamId1 = UUID.randomUUID();
        UUID teamId2 = UUID.randomUUID();

        List<RankingRowDTO> mockRanking = List.of(
                new RankingRowDTO(
                        new TeamDTO(teamId1, "Test Team 1"),
                        1, 10, 7, 2, 1, 15, 6, 9, 22
                ),
                new RankingRowDTO(
                        new TeamDTO(teamId2, "Test Team 2"),
                        2, 10, 5, 3, 2, 12, 8, 4, 18
                )
        );

        // Configure the mock
        when(dataSoccerService.getRanking()).thenReturn(mockRanking);

        // Fill the database
        jdbcSoccerService.fillDatabase();

        // Test the getRanking method
        List<RankingRowDTO> ranking = jdbcSoccerService.getRanking();

        // Assertions
        assertNotNull(ranking);
        assertEquals(2, ranking.size());
        assertEquals(1, ranking.get(0).rank());
        assertEquals(2, ranking.get(1).rank());
    }
}