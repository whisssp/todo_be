package com.todo_quqo.exception;

import com.todo_quqo.enums.EnumError;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@Data
public class CustomException extends RuntimeException{

    private EnumError error;

    public CustomException(EnumError error) {
        super(error.getErrorMessage());
        this.error = error;
    }
}