package com.todo_quqo.exception.handler;

import com.fasterxml.jackson.core.JsonParseException;
import com.todo_quqo.exception.CustomErrorMessage;
import com.todo_quqo.exception.CustomException;
import com.todo_quqo.exception.NotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.net.URI;

@RestControllerAdvice
@EnableWebMvc
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(value = CustomException.class)
    public ResponseEntity<CustomErrorMessage> exception(CustomException e) {
        return ResponseEntity.badRequest().body(new CustomErrorMessage(e.getError()));
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public ResponseEntity<ProblemDetail> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex, HttpServletRequest request) {
        ProblemDetail problem = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problem.setType(URI.create(request.getMethod()));
        problem.setTitle("InvalidBody");
        problem.setDetail(ex.getFieldError().getDefaultMessage());
        problem.setProperty("fieldError", ex.getFieldError().getField().toLowerCase());
        return ResponseEntity.badRequest().body(problem);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(value = Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ProblemDetail> handleException(Exception e, HttpServletRequest request) {
        ProblemDetail problem = ProblemDetail.forStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        problem.setTitle("INTERNAL_SERVER_ERROR");
        problem.setProperty("errorMessage", e.getMessage());
        problem.setDetail(e.toString());
        problem.setType(URI.create(request.getMethod()));
        problem.setInstance(URI.create("/todo/error"));
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(problem);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(org.springframework.web.servlet.NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ProblemDetail> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpServletRequest request) {
        ProblemDetail problem = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);
        problem.setTitle("NotFound");
        problem.setDetail("Cannot find resource.");
        problem.setType(URI.create(request.getMethod()));
        problem.setInstance(URI.create("/todo/error"));
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(problem);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(value = NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<CustomErrorMessage> handleNotFoundEntityException(NotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new CustomErrorMessage(ex.getError()));
    }
}