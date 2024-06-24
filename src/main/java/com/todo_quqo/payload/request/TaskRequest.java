package com.todo_quqo.payload.request;

import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.data.annotation.AccessType;

import java.time.Instant;

@Data
public class TaskRequest {

    private String id;

    @NotNull(message = "[title] is required")
    @NotEmpty(message = "[title] must be not empty")
    private String title;

    @NotNull(message = "[description] is required")
    @NotEmpty(message = "[description] must be not empty")
    private String description;

    private Boolean completed;

//    private Instant createdAt;
}