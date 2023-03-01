package com.squad.management.teams;

import com.squad.management.roles.RoleRepository;
import com.squad.management.teams.dto.CreateTeamRequest;
import com.squad.management.users.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CreateTeamUserService {

    private final TeamRepository teamRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public CreateTeamUserService(TeamRepository teamRepository, UserRepository userRepository, RoleRepository roleRepository) {
        this.teamRepository = teamRepository;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Transactional
    public Team createTeam(CreateTeamRequest request) {
        return teamRepository.save(request.toDomain(userRepository, roleRepository));
    }
}
