package com.project.services;

import com.project.dto.MatchDTO;
import com.project.dto.TeamDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class JpaSoccerServiceTest {
    @Autowired
    private JpaSoccerService jpaSoccerService;

    @Autowired
    private DataSoccerService dataSoccerService;



    @Test
    void testGetTeams() {
        // Given
        List<TeamDTO> expectedTeams = dataSoccerService.getTeams();
        // When
        List<TeamDTO> teams = jpaSoccerService.getTeams();
        // Then
        assertThat(teams, containsInAnyOrder(expectedTeams.toArray()));
    }

    @Test
    void testGetMatches() {
        // Given
        List<TeamDTO> teams = dataSoccerService.getTeams();
        UUID teamId = teams.get(4).id();
        List<MatchDTO> expectedMatches = dataSoccerService.getMatches(teamId);
        // When
        List<MatchDTO> matches = jpaSoccerService.getMatches(teamId);
        // Then
        assertEquals(expectedMatches, matches);
    }




}