package com.squad.management.teams.dto;

import com.squad.management.teams.Team;

public record ListTeamsResponse(
        Long id,
        String name
) {
    public static ListTeamsResponse fromDomain(Team team) {
        return new ListTeamsResponse(team.getId(), team.getName());
    }
}
