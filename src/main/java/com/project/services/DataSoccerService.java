package com.project.services;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.project.dto.RankingRowDTO;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;


@Service
@Primary

public class DataSoccerService implements SoccerService {
    private final ObjectMapper objectMapper;

    DataSoccerService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }
    private <T> List<T> getList(String filename, Class<T> class_) {
        try {
            File file = ResourceUtils.getFile("classpath:data/"+filename);
            JavaType type = objectMapper.getTypeFactory().constructCollectionType(List.class, class_);
            objectMapper.registerModule(new JavaTimeModule());
            return objectMapper.readValue(file, type);
        } catch (IOException e) {
            return Collections.emptyList();
        }
    }
    @Override

    public List<RankingRowDTO> getRanking() {
        return getList("ranking.json", RankingRowDTO.class);
    }
}