package com.squad.management.teams.dto;

import com.squad.management.memberships.Membership;
import com.squad.management.teams.Team;
import com.squad.management.users.User;

import java.util.HashSet;
import java.util.List;
import java.util.UUID;

public record TeamResponse(
        Long id,
        String name,
        Long teamLeadId,
        List<Long> teamMemberIds
) {
    public static TeamResponse fromDomain(Team team) {
        return new TeamResponse(
                team.getId(),
                team.getName(),
                team.getTeamLead() != null ? team.getTeamLead().getId() : null,
                team.getMemberships().stream().map(member -> member.getUser().getId()).toList()
        );
    }
}
