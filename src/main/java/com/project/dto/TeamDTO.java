package com.project.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record TeamDTO(
        UUID id,
        @NotBlank
        @Size(min = 3, max = 20)
        String name
) {}