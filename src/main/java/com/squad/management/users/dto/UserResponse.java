package com.squad.management.users.dto;

import com.squad.management.users.User;

import java.util.UUID;

public record UserResponse(
        Long id,
        String firstName,
        String lastName,
        String displayName,
        String avatarUrl,
        String location
) {
    public static UserResponse fromDomain(User user) {
        return new UserResponse(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getDisplayName(),
                user.getAvatarUrl(),
                user.getLocation()
        );
    }
}
