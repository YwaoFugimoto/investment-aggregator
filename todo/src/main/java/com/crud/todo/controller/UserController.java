package com.crud.todo.controller;

import com.crud.todo.entity.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/users")
public class UserController {

    @PostMapping
    public ResponseEntity<User> createUser(@ResquestBody CreateUserDto createUserDto) {
        //
        return null;
    }

    @GetMapping
    public ResponseEntity<User> getUserById(@PathVariable("userId") String userId) {
        //
        return null;
    }
}
