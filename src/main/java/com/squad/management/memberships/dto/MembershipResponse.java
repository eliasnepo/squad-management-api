package com.squad.management.memberships.dto;

public record MembershipResponse(
        String teamMember,
        String teamName,
        String role
) {
}
