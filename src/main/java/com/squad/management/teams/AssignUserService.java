package com.squad.management.teams;

import com.squad.management.exceptions.ResourceNotFoundException;
import com.squad.management.memberships.Membership;
import com.squad.management.roles.Role;
import com.squad.management.roles.RoleRepository;
import com.squad.management.users.User;
import com.squad.management.users.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AssignUserService {

    private final TeamRepository teamRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public AssignUserService(TeamRepository teamRepository, UserRepository userRepository, RoleRepository roleRepository) {
        this.teamRepository = teamRepository;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Transactional
    public void assignUserToTeam(Long id, Long userId) {
        var team = teamRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Team not found with id " + id));

        var user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + userId));

        var role = roleRepository.findByName("Developer");

        team.getMemberships().add(new Membership(
                user,
                team,
                role
        ));
    }
}
