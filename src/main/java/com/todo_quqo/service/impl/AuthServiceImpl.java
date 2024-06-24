package com.todo_quqo.service.impl;

import com.todo_quqo.controller.mapper.UserResponseMapper;
import com.todo_quqo.entity.User;
import com.todo_quqo.jwt.JwtProvider;
import com.todo_quqo.payload.request.UserRegisterRequest;
import com.todo_quqo.payload.response.LoginResponse;
import com.todo_quqo.payload.response.UserRegisterResponse;
import com.todo_quqo.service.AuthService;
import com.todo_quqo.service.LoginRequest;
import com.todo_quqo.service.UserDetailsExtService;
import com.todo_quqo.service.UserService;
import com.todo_quqo.service.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final UserDetailsExtService userDetailsServiceExt;

    private final UserService userService;

    private final AuthenticationManager authenticationManager;

    private final PasswordEncoder passwordEncoder;

    private final JwtProvider jwtProvider;

    private final UserMapper userMapper;

    private final UserResponseMapper userResponseMapper;

    public AuthServiceImpl(UserDetailsExtService userDetailsServiceExt, UserService userService, AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder, JwtProvider jwtProvider, UserMapper userMapper, UserResponseMapper userResponseMapper) {
        this.userDetailsServiceExt = userDetailsServiceExt;
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.jwtProvider = jwtProvider;
        this.userMapper = userMapper;
        this.userResponseMapper = userResponseMapper;
    }

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        log.info("Request to Login with: {}", loginRequest.toString());
        authenticate(loginRequest.getEmail(), loginRequest.getPassword());
        String token = jwtProvider.generateToken(SecurityContextHolder.getContext().getAuthentication(), loginRequest.getRememberMe(), null);
        return new LoginResponse(token);
    }

    @Override
    public UserRegisterResponse register(UserRegisterRequest registerRequest) {
        registerRequest.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        User userRegistered = userService.save(userMapper.toEntity(registerRequest));
        return userResponseMapper.toDto(userRegistered);
    }

    private void authenticate(String username, String password) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                userDetailsServiceExt.loadUserByUsername(username), password);
        try {
            Authentication authentication = authenticationManager.authenticate(authenticationToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (BadCredentialsException e) {
            log.error(">>>Err/Login: {}", e.getMessage());
            throw new BadCredentialsException("Invalid Username or Password!");
        }
    }
}