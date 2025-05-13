package com.crud.todo.service;

import com.crud.todo.controller.CreateUserDto;
import com.crud.todo.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    public UserRepository userRepository;

    public UserService (UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void createUSer(CreateUserDto createUserDto) {

    }
}
