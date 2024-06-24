package com.todo_quqo.exception.handler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.Serializable;
import java.net.URI;


@RestControllerAdvice
public class AccessDeniedExceptionHandler implements Serializable {

    @ExceptionHandler({AccessDeniedException.class})
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResponseEntity<ProblemDetail> handleAccessDeniedException(HttpServletRequest request,
                                                                     HttpServletResponse response,
                                                                     AccessDeniedException ex) {
        ProblemDetail problem = ProblemDetail.forStatus(HttpStatus.FORBIDDEN);
        problem.setTitle("AccessDenied");
        problem.setInstance(URI.create(request.getRequestURI()));
        problem.setDetail("You don't have permission to access this resource");
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(problem);
    }
}