package com.loginregister.role;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;

/**
 * A DTO for the {@link Role} entity
 */
public record RoleDto(@NotBlank String roleName) implements Serializable {
}