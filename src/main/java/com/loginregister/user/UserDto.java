package com.loginregister.user;

import jakarta.validation.constraints.NotBlank;

import java.io.Serializable;

/**
 * A DTO for the {@link User} entity
 */
public record UserDto(@NotBlank String username, @NotBlank String password) implements Serializable {
}