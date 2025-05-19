package com.crud.todo.controller.responseDto;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public record ResponseUserDto(UUID userId, String username, String email, String password, Instant creationTimestamp, Instant updateTimestamp, List<ResponseAccountDto> accounts) {
}
