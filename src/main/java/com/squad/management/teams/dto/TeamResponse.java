package com.squad.management.teams.dto;

import com.squad.management.teams.Team;

import java.util.List;

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
                team.getTeamLead().getId(),
                team.getMemberships().stream().map(member -> member.getUser().getId()).toList()
        );
    }
}
