package com.crud.todo.controller.responseDto;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public record UserResponseDto(UUID userId, String username, String email, String password, Instant creationTimestamp, Instant updateTimestamp, List<AccountResponseDto> accounts) {
}
