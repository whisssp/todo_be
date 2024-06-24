package com.todo_quqo.service.mapper;

import com.todo_quqo.entity.User;
import com.todo_quqo.payload.request.UserRegisterRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface UserMapper extends EntityMapper<UserRegisterRequest, User> {
}