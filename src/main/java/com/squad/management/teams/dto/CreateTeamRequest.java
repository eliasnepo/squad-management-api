package com.squad.management.teams.dto;

import com.squad.management.memberships.Membership;
import com.squad.management.roles.Role;
import com.squad.management.roles.RoleRepository;
import com.squad.management.teams.Team;
import com.squad.management.users.User;
import com.squad.management.users.UserRepository;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.util.Pair;

import java.util.Optional;
import java.util.Set;

public record CreateTeamRequest(
        @NotBlank String name,
        Long teamLeadId
) {
    public Team toDomain(UserRepository userRepository, RoleRepository roleRepository) {
        return Optional.ofNullable(teamLeadId)
                    .map(userRepository::getReferenceById)
                    .map(teamLeadUser -> {
                        var role = roleRepository.findByName("Developer");
                        return Pair.of(teamLeadUser, role);
                    })
                    .map(pair -> {
                        var team = new Team(null, name, pair.getFirst());
                        team.addMembership(new Membership(pair.getFirst(), team, pair.getSecond()));
                        return team;
                    })
                    .orElse(new Team(null, name, null));
    }
}
