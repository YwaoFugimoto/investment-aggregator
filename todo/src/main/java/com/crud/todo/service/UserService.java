package com.crud.todo.service;

import com.crud.todo.controller.responseDto.AccountResponseDto;
import com.crud.todo.controller.dto.CreateAccountDto;
import com.crud.todo.controller.dto.CreateUserDto;
import com.crud.todo.controller.dto.UpdateUserDto;
import com.crud.todo.controller.responseDto.UserResponseDto;
import com.crud.todo.entity.Account;
import com.crud.todo.entity.BillingAddress;
import com.crud.todo.entity.User;
import com.crud.todo.repository.AccountRepository;
import com.crud.todo.repository.BillingAddressRepository;
import com.crud.todo.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static java.util.stream.Collectors.toList;

@Service
public class UserService {

    private UserRepository userRepository;

    private AccountRepository accountRepository;

    private BillingAddressRepository billingAddressRepository;

    public UserService (UserRepository userRepository, AccountRepository accountRepository, BillingAddressRepository billingAddressRepository) {
        this.userRepository = userRepository;
        this.accountRepository = accountRepository;
        this.billingAddressRepository = billingAddressRepository;
    }

    public UUID createUSer(CreateUserDto createUserDto) {

        // dto -> entity
        var entity = new User(null, createUserDto.username(), createUserDto.email(),createUserDto.password(), Instant.now(), null);

        var userSaved = userRepository.save(entity);

        return userSaved.getUserId();
    }

    // list a user by id
    public Optional<UserResponseDto> getUserById (String userId){
        // if user isPresent() create response dto, else return isEmpty()
        return userRepository
                .findById(UUID.fromString(userId))
                .map(user -> new UserResponseDto(
                        user.getUserId(),
                        user.getUsername(),
                        user.getEmail(),
                        user.getPassword(),
                        user.getCreationTimestamp(),
                        user.getUpdateTimestamp(),
                        user.getAccounts().stream()
                                .map(a -> new AccountResponseDto(a.getAccountId().toString(), a.getDescription()))
                                .toList()
                ));
    }

    // list all users
    public List<UserResponseDto> listUsers() {

        return userRepository.findAll().stream().map(user -> new UserResponseDto(
                user.getUserId(),
                user.getUsername(),
                user.getEmail(),
                user.getPassword(),
                user.getCreationTimestamp(),
                user.getUpdateTimestamp(),
                user.getAccounts().stream().map(account -> new AccountResponseDto(account.getAccountId().toString(), account.getDescription())).toList())).toList();
    }

    public void updateUserById(String userId, UpdateUserDto updateUserDto){
        var id = UUID.fromString(userId);

        var userEntity = userRepository.findById(id);

        if (userEntity.isPresent()) {
            var user = userEntity.get();

            if(updateUserDto.username() != null) {
                user.setUsername(updateUserDto.username());
            }

            if(updateUserDto.password() != null) {
                user.setPassword(updateUserDto.password());
            }

            userRepository.save(user);
        }
    }

    public void deleteById(String userId) {
        var id = UUID.fromString(userId);

        var userExists = userRepository.existsById(id);

        if ( userExists ) {
            //delete all relations
            billingAddressRepository.deleteById(id);
            accountRepository.deleteById(id);
            userRepository.deleteById(id);
        }
    }

    // create account FIND: inf loop somewhere
    public void createAccount(String userId, CreateAccountDto createAccountDto) {

        // finding user todo - create function
        var user = userRepository.findById(UUID.fromString(userId))
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        // dto -> entity
        var account = new Account(
                UUID.randomUUID(),
                user, // user to relate
                null, // todo - create billing address
                createAccountDto.description(),
                new ArrayList<>() // empty list
        );

        // save and return entity
        var accountCreated = accountRepository.save(account);

        var billingAddress = new BillingAddress(
                accountCreated.getAccountId(),
                account, // account to relate
                createAccountDto.street(),
                createAccountDto.number()
        );

        billingAddressRepository.save(billingAddress);
    }

    // inf loop somewhere
    // list all accounts of ONE user
    public List<AccountResponseDto> listAccounts(String userId) {

        // finding user
        var user = userRepository.findById(UUID.fromString(userId))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        // returning a list of AccountResponseDto
        return user.getAccounts().stream().map(account -> new AccountResponseDto(account.getAccountId().toString(), account.getDescription())).toList();
    }
}




















