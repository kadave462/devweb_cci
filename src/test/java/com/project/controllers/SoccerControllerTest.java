package com.project.controllers;

import com.project.dto.MatchDTO;
import com.project.dto.TeamDTO;
import com.project.dto.RankingRowDTO;
import com.project.services.DataSoccerService;
import com.project.services.SoccerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.stringContainsInOrder;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SoccerController.class)
public class SoccerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DataSoccerService dataSoccerService;

    @Test
    public void testRanking() throws Exception {
        UUID teamId0 = UUID.fromString("aabd33ba-2c89-43e7-903d-0cd15295128e");
        UUID teamId1 = UUID.fromString("aabd33ba-3c89-43e7-903d-2ce15295128e");

        List<RankingRowDTO> rows = List.of(
                new RankingRowDTO(new TeamDTO(teamId0, "Marseille"), 3, 38, 22, 10, 6, 111, 92, 19, 72),
                new RankingRowDTO(new TeamDTO(teamId1, "Paris"), 5, 38, 19, 15, 4, 86, 80, 6, 61)
        );

        when(dataSoccerService.getRanking()).thenReturn(rows);

        // When
        MvcResult result = mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andReturn();
        String html = result.getResponse().getContentAsString();

        // Then
        // Ensure that the HTML contains the expected content
        assertThat(html, stringContainsInOrder("N°", "Équipe", "MJ", "G", "N", "P", "BP", "BC", "DB", "Pts"));
        assertThat(html, stringContainsInOrder("3", "<a href=\"/team/" + teamId0 + "\">Marseille</a>", "38", "22", "10", "6", "111", "92", "19", "72"));
        assertThat(html, stringContainsInOrder("5", "<a href=\"/team/" + teamId1 + "\">Paris</a>", "38", "19", "15", "4", "86", "80", "6", "61"));
    }
    @Test
    public void testTeam() throws Exception {
        // Given
        UUID teamId = UUID.fromString("aabd33ba-2c89-43e7-903d-0cd15295128e");
        RankingRowDTO rankingRow = new RankingRowDTO(
                new TeamDTO(teamId, "Marseille"),
                3, 38, 22, 10, 6, 111, 92, 19, 72);

        // Create test match data
        UUID otherTeamId = UUID.fromString("f4e1f6b4-ecf8-4c8e-97f8-7ca76659816a");
        List<MatchDTO> matches = List.of(
                new MatchDTO(
                        UUID.randomUUID(),
                        new TeamDTO(teamId, "Marseille"),
                        new TeamDTO(otherTeamId, "Le Havre"),
                        1, 4, LocalDate.of(2048, 8, 3), LocalTime.of(21, 0)
                ),
                new MatchDTO(
                        UUID.randomUUID(),
                        new TeamDTO(otherTeamId, "Le Havre"),
                        new TeamDTO(teamId, "Marseille"),
                        3, 1, LocalDate.of(2048, 8, 10), LocalTime.of(21, 0)
                )
        );

        when(dataSoccerService.getRankingRow(teamId)).thenReturn(rankingRow);
        when(dataSoccerService.getMatches(teamId)).thenReturn(matches);

        // When
        MvcResult result = mockMvc.perform(get("/team/" + teamId))
                .andExpect(status().isOk())
                .andReturn();
        String html = result.getResponse().getContentAsString();

        // Then
        verify(dataSoccerService, times(1)).getRankingRow(teamId);
        verify(dataSoccerService, times(1)).getMatches(teamId);

        // Verify ranking info
        assertThat(html, stringContainsInOrder("N°", "Équipe", "MJ", "G", "N", "P", "BP", "BC", "DB", "Pts"));
        assertThat(html, stringContainsInOrder("3", "Marseille", "38", "22", "10", "6", "111", "92", "19", "72"));

        // Verify match info
        assertThat(html, containsString("03/08/2048 21:00"));
        assertThat(html, containsString("10/08/2048 21:00"));
        assertThat(html, containsString("1 - 4"));
        assertThat(html, containsString("3 - 1"));
        assertThat(html, containsString("/team/" + teamId));
        assertThat(html, containsString("/team/" + otherTeamId));
        assertThat(html, containsString("Marseille"));
        assertThat(html, containsString("Le Havre"));
    }
}

