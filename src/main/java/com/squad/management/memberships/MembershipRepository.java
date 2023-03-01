package com.squad.management.memberships;

import com.squad.management.roles.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.Set;

public interface MembershipRepository extends JpaRepository<Membership, MembershipId> {

    @Query("SELECT m FROM Membership m WHERE m.user.id = :userId AND m.team.id = :teamId")
    Membership findByUserIdAndTeamId(Long userId, Long teamId);

    Set<Membership> findAllByRoleId(Long roleId);

    default Optional<Role> findRoleByUserIdAndTeamId(Long userId, Long teamId) {
        Membership membership = findByUserIdAndTeamId(userId, teamId);
        return membership != null ? Optional.of(membership.getRole()) : Optional.empty();
    }
}
