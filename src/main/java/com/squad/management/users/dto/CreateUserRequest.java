package com.squad.management.users.dto;

import com.squad.management.users.User;
import jakarta.validation.constraints.NotBlank;

public record CreateUserRequest(
        @NotBlank String firstName,
        @NotBlank String lastName,
        @NotBlank String displayName,
        @NotBlank String avatarUrl,
        @NotBlank String location

) {
    public User toDomain() {
        return new User(null, firstName, lastName, displayName, avatarUrl, location);
    }
}
