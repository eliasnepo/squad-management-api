package com.squad.management.fixtures;

import com.squad.management.memberships.Membership;
import com.squad.management.roles.Role;
import com.squad.management.teams.Team;

public class TeamFactory {

    public static Team getTeamWithTeamLead(Role role) {
        var team =  new Team(
                1L,
                "Product A",
                UserFactory.getUser()
        );
        team.addMembership(new Membership(UserFactory.getUser(), team, role));
        return team;
    }

    public static Team getTeamWithoutTeamLead() {
        return new Team(
                1L,
                "Product A",
                null
        );
    }
}
