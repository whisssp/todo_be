package com.todo_quqo.service;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link com.todo_quqo.entity.User}
 */
@Data
public class LoginRequest {

    private String email;

    private String password;

    private Boolean rememberMe;
}