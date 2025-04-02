package com.project.dto;

import jakarta.validation.constraints.NotBlank;

public class TeamFormDTO {
    @NotBlank(message = "Le nom de l'équipe est obligatoire")
    private String name;

    // Getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}