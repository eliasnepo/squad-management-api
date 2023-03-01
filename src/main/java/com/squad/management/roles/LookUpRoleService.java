package com.squad.management.roles;

import com.squad.management.exceptions.ResourceNotFoundException;
import com.squad.management.memberships.MembershipRepository;
import com.squad.management.memberships.dto.MembershipResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class LookUpRoleService {

    private final MembershipRepository membershipRepository;

    public LookUpRoleService(MembershipRepository membershipRepository) {
        this.membershipRepository = membershipRepository;
    }

    @Transactional(readOnly = true)
    public String lookUpRoleForAMembership(Long teamId, Long userId) {
        return membershipRepository.findRoleByUserIdAndTeamId(userId, teamId)
                .map(role -> role.getName())
                .orElseThrow(() -> new ResourceNotFoundException("Membership not found."));
    }

    @Transactional(readOnly = true)
    public Set<MembershipResponse> lookUpMembershipsForARole(Long roleId) {
        return membershipRepository
                .findAllByRoleId(roleId)
                .stream()
                .map(membership -> new MembershipResponse(
                        membership.getUser().getFirstName() + " " + membership.getUser().getLastName(),
                        membership.getTeam().getName(),
                        membership.getRole().getName()
                ))
                .collect(Collectors.toSet());
    }
}
