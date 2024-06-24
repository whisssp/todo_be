package com.todo_quqo.service.mapper;

import com.todo_quqo.entity.Task;
import com.todo_quqo.payload.request.TaskRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface TaskMapper extends EntityMapper<TaskRequest, Task>{
}