package com.todo_quqo.controller;

import com.todo_quqo.entity.Task;
import com.todo_quqo.payload.request.TaskRequest;
import com.todo_quqo.payload.response.PaginationResponse;
import com.todo_quqo.payload.response.TaskResponse;
import com.todo_quqo.service.TaskService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/api/v0")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping("/todo")
    public ResponseEntity<?> createTask(@Valid @RequestBody TaskRequest payload) {
        log.debug("REST to create a new Task");
        taskService.save(payload);
        return ResponseEntity.noContent().build();
    }

    @GetMapping({"/todos", "/public/todos"})
    public ResponseEntity<PaginationResponse<TaskResponse>> getTasks(Pageable pageable) {
        log.debug("REST to get all tasks");
        return ResponseEntity.ok(taskService.getTasks(pageable));
    }

    @GetMapping("/todo/{id}")
    public ResponseEntity<TaskResponse> getTaskById(@PathVariable("id") String id) {
        log.debug("REST to get Task by ID: {}", id);
        return ResponseEntity.ok(taskService.getTaskById(id));
    }

    @PutMapping("/todo/{id}")
    public ResponseEntity<TaskResponse> updateTaskById(@PathVariable("id") String id, @RequestBody TaskRequest payload) {
        log.debug("REST to update Task ID: {}", id);
        return ResponseEntity.ok(taskService.updateTaskById(id, payload));
    }

    @DeleteMapping("/todo/{id}")
    public ResponseEntity<Void> deleteTaskById(@PathVariable("id") String id) {
        log.debug("REST to delete Task ID: {}", id);
        taskService.delete(id);
        return ResponseEntity.noContent().build();
    }
}