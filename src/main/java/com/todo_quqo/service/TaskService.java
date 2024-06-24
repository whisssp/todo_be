package com.todo_quqo.service;

import com.todo_quqo.entity.Task;
import com.todo_quqo.payload.request.TaskRequest;
import com.todo_quqo.payload.response.PaginationResponse;
import com.todo_quqo.payload.response.TaskResponse;
import org.springframework.data.domain.Pageable;

public interface TaskService {

    void save(TaskRequest taskRequest);

    TaskResponse getTaskById(String id);

    PaginationResponse<TaskResponse> getTasks(Pageable pageable);

    void delete(String id);

    TaskResponse updateTaskById(String id, TaskRequest taskRequest);
}