package com.todo_quqo.service.impl;

import com.todo_quqo.controller.mapper.TaskResponseMapper;
import com.todo_quqo.enums.EnumError;
import com.todo_quqo.exception.CustomException;
import com.todo_quqo.entity.Task;
import com.todo_quqo.exception.NotFoundException;
import com.todo_quqo.payload.request.TaskRequest;
import com.todo_quqo.payload.response.PaginationResponse;
import com.todo_quqo.payload.response.TaskResponse;
import com.todo_quqo.repository.TaskRepository;
import com.todo_quqo.service.TaskService;
import com.todo_quqo.service.mapper.TaskMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
@Slf4j
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

    private final TaskMapper taskMapper;

    private final TaskResponseMapper taskResponseMapper;

    public TaskServiceImpl(TaskRepository taskRepository, TaskMapper taskMapper,
                           TaskResponseMapper taskResponseMapper) {
        this.taskRepository = taskRepository;
        this.taskMapper = taskMapper;
        this.taskResponseMapper = taskResponseMapper;
    }

    @Override
    public void save(TaskRequest taskRequest) {
        log.debug("Request to store new task");
        Task newTask = taskMapper.toEntity(taskRequest);

        log.debug(">>>Task before storing: {}", newTask);
        taskRepository.save(newTask);
    }

    @Override
    public TaskResponse getTaskById(String id) {
        log.debug("Request to get task with task ID: {}", id);
        return taskResponseMapper.toDto(this.findTaskById(id));
    }

    @Override
    public PaginationResponse<TaskResponse> getTasks(Pageable pageable) {
        log.debug("Request to get all tasks");
        return PaginationResponse.toPaginationResponse(
                taskRepository.findAll(pageable).map(taskResponseMapper::toDto));
    }

    @Override
    public void delete(String id) {
        log.debug("Request to delete task with task ID: {}", id);
        Task task = this.findTaskById(id);
        if (task == null) throw new NotFoundException(EnumError.TASK_NOT_FOUND);
        taskRepository.delete(task);
    }

    @Override
    public TaskResponse updateTaskById(String id, TaskRequest taskRequest) {
        log.debug("Request to update task by ID: {}", id);
        Task taskUpdate = findTaskById(id);
        taskMapper.partialUpdate(taskUpdate, taskRequest);

        log.debug(">>>Task updated before storing: {}", taskUpdate);
        return taskResponseMapper.toDto(taskRepository.save(taskUpdate));
    }

    private Task findTaskById(String id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(EnumError.TASK_NOT_FOUND));
    }
}