package com.crud.todo.service;

import com.crud.todo.controller.CreateUserDto;
import com.crud.todo.entity.User;
import com.crud.todo.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

//triple way
//arrange
//act
//asset
@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Nested
    class createUser {
        @Test
        @DisplayName("Should create a user successfully")
        void shouldCreateUser() {

            var user = new User(
                    UUID.randomUUID(),
                    "username",
                    "email@email.com",
                    "password",
                    Instant.now(),
                    null
            );
            doReturn(user).when(userRepository).save(any());
            var input = new CreateUserDto("username", "email@email.com", "password");

            //act
            var output = userService.createUSer(input);

            //assert
            assertNotNull(output);
        }
    }
}
























