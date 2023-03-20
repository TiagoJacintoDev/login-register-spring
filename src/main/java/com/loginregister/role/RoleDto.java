package com.loginregister.role;

import jakarta.validation.constraints.NotBlank;

import java.io.Serializable;

/**
 * A DTO for the {@link RoleModel} entity
 */
public record RoleDto(@NotBlank String roleName) implements Serializable {
}