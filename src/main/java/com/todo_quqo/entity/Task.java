package com.todo_quqo.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Data
@Table(name = "tasks", schema = "public")
@Entity
public class Task {

    @Id
    private String id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "completed")
    private Boolean completed;

    @Column(name = "created_at")
    private Instant createdAt;


    @PrePersist
    protected void onCreate() {
        if (id == null) {
            id = UUID.randomUUID().toString();
        }
        if (createdAt == null) {
            createdAt = Instant.now();
        }
    }
}