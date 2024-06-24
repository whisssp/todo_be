package com.todo_quqo.payload.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link com.todo_quqo.entity.User}
 */
@Data
public class UserRegisterRequest {

    private String name;

    @NotNull(message = "email is required")
    @NotEmpty(message = "email must not be empty")
    @NotBlank
    private String email;

    private String phone;

    @NotNull(message = "password is required")
    @NotEmpty(message = "password must not be empty")
    private String password;
}