package com.todo_quqo.payload.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.Instant;

@Data
public class TaskResponse {

    private String id;

    private String title;

    private String description;

    private Boolean completed;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", shape = JsonFormat.Shape.STRING, timezone = "UTC")
    private Instant createdAt;
}