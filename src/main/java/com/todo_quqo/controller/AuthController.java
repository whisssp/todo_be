package com.todo_quqo.controller;

import com.todo_quqo.payload.request.UserRegisterRequest;
import com.todo_quqo.payload.response.LoginResponse;
import com.todo_quqo.payload.response.UserRegisterResponse;
import com.todo_quqo.service.AuthService;
import com.todo_quqo.service.LoginRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/api/v0")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/authenticate")
    public ResponseEntity<LoginResponse> doLogin(@RequestBody LoginRequest loginRequest) {
        log.info("Rest to login with: {}", loginRequest.toString());
        return ResponseEntity.ok(authService.login(loginRequest));
    }

    @PostMapping("/register")
    public ResponseEntity<UserRegisterResponse> register(@Valid @RequestBody UserRegisterRequest userRegisterRequest) {
        log.debug("Rest to register user account");
        return ResponseEntity.ok(authService.register(userRegisterRequest));
    }
}