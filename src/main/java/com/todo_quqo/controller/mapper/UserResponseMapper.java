package com.todo_quqo.controller.mapper;

import com.todo_quqo.entity.User;
import com.todo_quqo.payload.response.UserRegisterResponse;
import com.todo_quqo.service.mapper.EntityMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface UserResponseMapper extends EntityMapper<UserRegisterResponse, User> {
}