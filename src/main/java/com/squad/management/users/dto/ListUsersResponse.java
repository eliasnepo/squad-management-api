package com.squad.management.users.dto;

import com.squad.management.users.User;

public record ListUsersResponse(
        Long id,
        String displayName
) {
    public static ListUsersResponse fromDomain(User user) {
        return new ListUsersResponse(user.getId(), user.getDisplayName());
    }
}
