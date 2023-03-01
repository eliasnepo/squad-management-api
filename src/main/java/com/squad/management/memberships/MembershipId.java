package com.squad.management.memberships;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class MembershipId {

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "team_id")
    private Long teamId;

    @Column(name = "role_id")
    private Long roleId;

    /* Necessary because of JPA reflection */
    public MembershipId() {}

    public MembershipId(Long userId, Long teamId, Long roleId) {
        this.userId = userId;
        this.teamId = teamId;
        this.roleId = roleId;
    }

    public Long getUserId() {
        return userId;
    }

    public Long getTeamId() {
        return teamId;
    }

    public Long getRoleId() {
        return roleId;
    }
}
