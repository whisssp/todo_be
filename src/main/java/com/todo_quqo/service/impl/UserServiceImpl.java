package com.todo_quqo.service.impl;

import com.todo_quqo.entity.User;
import com.todo_quqo.enums.EnumError;
import com.todo_quqo.exception.CustomException;
import com.todo_quqo.repository.UserRepository;
import com.todo_quqo.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findUserByEmail(email).orElseThrow(() -> new CustomException(EnumError.EMAIL_NOT_FOUND));
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }
}