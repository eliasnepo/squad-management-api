package com.squad.management.roles;

import com.squad.management.exceptions.ResourceNotFoundException;
import com.squad.management.memberships.Membership;
import com.squad.management.memberships.MembershipId;
import com.squad.management.memberships.MembershipRepository;
import com.squad.management.memberships.dto.MembershipResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AssignRoleService {

    private final MembershipRepository membershipRepository;
    private final RoleRepository roleRepository;

    public AssignRoleService(MembershipRepository membershipRepository, RoleRepository roleRepository) {
        this.membershipRepository = membershipRepository;
        this.roleRepository = roleRepository;
    }

    @Transactional
    public MembershipResponse assignRoleToUser(Long roleId, Long userId, Long teamId) {
        var role = roleRepository.findById(roleId)
                .orElseThrow(() -> new ResourceNotFoundException("Role not found with id " + roleId));

        return membershipRepository
                .findById(new MembershipId(userId, teamId))
                .stream()
                .peek(membership -> membership.setRole(role))
                .findFirst()
                .map(membership -> new MembershipResponse(
                        membership.getUser().getFirstName() + " " + membership.getUser().getLastName(),
                        membership.getTeam().getName(),
                        membership.getRole().getName())
                )
                .orElseThrow(() -> new ResourceNotFoundException("Membership not found for user " + userId + " in team " + teamId));
    }
}
