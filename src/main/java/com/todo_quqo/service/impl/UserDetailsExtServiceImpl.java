package com.todo_quqo.service.impl;

import com.todo_quqo.entity.CustomUserDetails;
import com.todo_quqo.entity.User;
import com.todo_quqo.repository.UserRepository;
import com.todo_quqo.service.UserDetailsExtService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsExtServiceImpl implements UserDetailsExtService {

    private final UserRepository userRepository;

    public UserDetailsExtServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findUserByEmail(username).orElse(null);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }

        return new CustomUserDetails(user);
    }

    @Override
    public UserDetails loadUserById(String id) throws UsernameNotFoundException {
        User user = userRepository.findById(Long.parseLong(id)).orElse(null);
        if (user == null) {
            throw new UsernameNotFoundException(id);
        }
        return new CustomUserDetails(user);
    }
}