package com.squad.management.teams.dto;

import com.squad.management.teams.Team;

import java.util.List;
import java.util.UUID;

public record TeamResponse(
        UUID id,
        String name,
        UUID teamLeadId,
        List<UUID> teamMemberIds
) {
    public static TeamResponse fromDomain(Team team) {
        return new TeamResponse(
                team.getId(),
                team.getName(),
                team.getTeamLead().getId(),
                team.getMemberships().stream().map(member -> member.getUser().getId()).toList()
        );
    }
}
