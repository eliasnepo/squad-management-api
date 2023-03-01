package com.squad.management.memberships;

import com.squad.management.roles.Role;
import com.squad.management.teams.Team;
import com.squad.management.users.User;
import jakarta.persistence.*;

@Entity
@Table(name = "tb_membership")
public class Membership {

    @EmbeddedId
    private MembershipId id = new MembershipId();

    @ManyToOne
    @MapsId("userId")
    private User user;

    @ManyToOne
    @MapsId("teamId")
    private Team team;

    @ManyToOne
    private Role role;

    /* Necessary because of JPA reflection */
    public Membership() {}

    public Membership(User user, Team team, Role role) {
        this.user = user;
        this.team = team;
        this.role = role;
    }

    public MembershipId getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public Team getTeam() {
        return team;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}

