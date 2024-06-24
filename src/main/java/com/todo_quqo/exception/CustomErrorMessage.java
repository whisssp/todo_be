package com.todo_quqo.exception;

import com.todo_quqo.enums.EnumError;
import lombok.Data;

@Data
public class CustomErrorMessage {

    private String entity;
    private String errorKey;
    private String message;

    public CustomErrorMessage(EnumError error) {
        this.entity = error.getEntityName();
        this.errorKey = error.getErrorKey();
        this.message = error.getErrorMessage();
    }
}