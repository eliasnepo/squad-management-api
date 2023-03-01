package com.squad.management.users.dto;

import com.squad.management.users.User;

import java.util.UUID;

public record ListUsersResponse(
        UUID id,
        String displayName
) {
    public static ListUsersResponse fromDomain(User user) {
        return new ListUsersResponse(user.getId(), user.getDisplayName());
    }
}
