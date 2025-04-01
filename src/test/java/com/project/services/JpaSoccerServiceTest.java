package com.project.services;

import com.project.dto.TeamDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;

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

}