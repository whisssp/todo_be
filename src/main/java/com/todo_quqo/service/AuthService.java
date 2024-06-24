package com.todo_quqo.service;

import com.todo_quqo.payload.request.UserRegisterRequest;
import com.todo_quqo.payload.response.LoginResponse;
import com.todo_quqo.payload.response.UserRegisterResponse;

public interface AuthService {

    UserRegisterResponse register(UserRegisterRequest registerRequest);

    LoginResponse login(LoginRequest loginRequest);
}