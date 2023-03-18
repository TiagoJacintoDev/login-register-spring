package com.loginregister.user;

import java.io.Serializable;

/**
 * A DTO for the {@link User} entity
 */
public record UserDto(String username, String password) implements Serializable {
}