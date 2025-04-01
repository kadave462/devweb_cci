package com.project.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;

@Entity
@Table(name = "teams")
public class Team {
    @Id
    public UUID id;

    @NotNull
    public String name;

    public Team() { }

    public Team(UUID id, String name) {
        this.id = id;
        this.name = name;
    }
}