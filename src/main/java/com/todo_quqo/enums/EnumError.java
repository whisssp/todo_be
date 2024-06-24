package com.todo_quqo.enums;

import lombok.Getter;

@Getter
public enum EnumError {

    TASK_NOT_FOUND("task", "NotFound", "task not found"),

    EMAIL_NOT_FOUND("users", "NotFound", "email not found"),

    ;

    private String entityName;

    private String errorKey;

    private String errorMessage;

    EnumError(String entityName, String errorKey, String errorMessage) {
        this.entityName = entityName;
        this.errorMessage = errorMessage;
        this.errorKey = errorKey;
    }

    EnumError(String entityName, String errorKey) {
        this.entityName = entityName;
        this.errorKey = errorKey;
    }

    public String getEntityName() {
        return entityName;
    }

    public String getErrorKey() {
        return errorKey;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}