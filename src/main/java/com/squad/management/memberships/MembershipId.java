package com.squad.management.memberships;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.util.UUID;

@Embeddable
public class MembershipId {

    @Column(name = "user_id")
    private UUID userId;

    @Column(name = "team_id")
    private UUID teamId;

    @Column(name = "role_id")
    private Long roleId;

    /* Necessary because of JPA reflection */
    public MembershipId() {}

    public MembershipId(UUID userId, UUID teamId, Long roleId) {
        this.userId = userId;
        this.teamId = teamId;
        this.roleId = roleId;
    }

    public UUID getUserId() {
        return userId;
    }

    public UUID getTeamId() {
        return teamId;
    }

    public Long getRoleId() {
        return roleId;
    }
}
