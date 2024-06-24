package com.todo_quqo.exception;

import com.todo_quqo.enums.EnumError;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NotFoundException extends RuntimeException {

    private EnumError error;

    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(EnumError error) {
        super(error.getErrorMessage());
        this.error = error;
    }
}