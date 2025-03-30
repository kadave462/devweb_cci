package com.project.services;

import com.project.dto.RankingRowDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class JdbcSoccerServiceTest {

    @Autowired
    private JdbcSoccerService jdbcSoccerService;

    @Autowired
    private DataSoccerService dataSoccerService;

    @Test
    public void testGetRanking() {
        List<RankingRowDTO> ranking = jdbcSoccerService.getRanking();
        List<RankingRowDTO> expectedRanking = dataSoccerService.getRanking();
        assertEquals(expectedRanking, ranking);
    }
}