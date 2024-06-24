package com.todo_quqo.payload.response;

import lombok.Data;

@Data
public class UserRegisterResponse {

    private Long id;

    private String email;

    private String password;

    private String name;

    private String phone;

}