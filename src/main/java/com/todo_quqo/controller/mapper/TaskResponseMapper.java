package com.todo_quqo.controller.mapper;

import com.todo_quqo.entity.Task;
import com.todo_quqo.payload.response.TaskResponse;
import com.todo_quqo.service.mapper.EntityMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface TaskResponseMapper extends EntityMapper<TaskResponse, Task> {
}