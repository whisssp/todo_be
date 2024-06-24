package com.todo_quqo.service;


import com.todo_quqo.entity.User;

public interface UserService {

    User getUserByEmail(String email);

    User save(User user);

}