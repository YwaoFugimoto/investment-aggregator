package com.crud.todo.controller;

import com.crud.todo.controller.responseDto.AccountResponseDto;
import com.crud.todo.controller.dto.CreateAccountDto;
import com.crud.todo.controller.dto.CreateUserDto;
import com.crud.todo.controller.dto.UpdateUserDto;
import com.crud.todo.controller.responseDto.UserResponseDto;
import com.crud.todo.entity.User;
import com.crud.todo.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/v1/users")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // create user
    @PostMapping
        public ResponseEntity<User> createUser(@RequestBody CreateUserDto createUserDto) {
        var userId = userService.createUSer(createUserDto);

        return ResponseEntity.created(URI.create("/v1/users/" + userId.toString())).build();
    }

    // find user
    @GetMapping("/{userId}")
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable("userId") String userId) {
        var maybeUser = userService.getUserById(userId);
        // return user if is present else return no found
        return maybeUser.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // display all users
    @GetMapping
    public ResponseEntity<List<UserResponseDto>> listUsers() {
        var users = userService.listUsers();
        
        return ResponseEntity.ok(users);
    }

    // update user
    @PutMapping("/{userId}")
    public ResponseEntity<Void> updateUserById(@PathVariable("userId") String userId,
                                               @RequestBody UpdateUserDto updateUserDto) {
        userService.updateUserById(userId, updateUserDto);
        return ResponseEntity.noContent().build();
    }

    // delete user
    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteById (@PathVariable("userId") String userId) {
        userService.deleteById(userId);
        return ResponseEntity.noContent().build();
    }

    // create account
    @PostMapping("/{userId}/accounts")
    public ResponseEntity<Void> createAccount (@PathVariable("userId") String userId,
                                           @RequestBody CreateAccountDto createAccountDto) {

        userService.createAccount(userId, createAccountDto);

        return ResponseEntity.ok().build();
    }

    // list all accounts of a specific user
    @GetMapping("/{userId}/accounts")
    public ResponseEntity <List<AccountResponseDto>> listAccounts (@PathVariable("userId") String userId) {
        return ResponseEntity.ok(userService.listAccounts(userId));
    }
}
