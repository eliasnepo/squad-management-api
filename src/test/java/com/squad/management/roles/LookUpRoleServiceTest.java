package com.squad.management.roles;

import com.squad.management.exceptions.ResourceNotFoundException;
import com.squad.management.fixtures.TeamFactory;
import com.squad.management.fixtures.UserFactory;
import com.squad.management.memberships.MembershipRepository;
import com.squad.management.teams.TeamRepository;
import com.squad.management.users.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest(includeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {LookUpRoleService.class, RoleRepository.class, MembershipRepository.class, UserRepository.class, TeamRepository.class}))
public class LookUpRoleServiceTest {

    @Autowired
    private LookUpRoleService service;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private MembershipRepository membershipRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TeamRepository teamRepository;

    @BeforeEach
    void setUp() {
        roleRepository.deleteAll();
        membershipRepository.deleteAll();
        teamRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("should retrieve role name given a existing membership")
    void test1() {
        var user = userRepository.save(UserFactory.getUser());
        var role = roleRepository.save(new Role(null, "QA"));
        var team = teamRepository.save(TeamFactory.getTeamWithTeamLead(role));

        var roleName = service.lookUpRoleForAMembership(team.getId(), user.getId());

        assertEquals(role.getName(), roleName);
    }

    @Test
    @DisplayName("should thrown exception given a membership that does not exist")
    void test2() {
        assertThrows(ResourceNotFoundException.class, () -> service.lookUpRoleForAMembership(Long.MAX_VALUE, Long.MAX_VALUE));
    }

    @Test
    @DisplayName("should retrieve list of membership given a role")
    void test3() {
        var user = userRepository.save(UserFactory.getUser());
        var role = roleRepository.save(new Role(null, "QA"));
        var team = teamRepository.save(TeamFactory.getTeamWithTeamLead(role));

        var membershipResponse = service.lookUpMembershipsForARole(role.getId());

        assertEquals(membershipResponse.size(), 1);
    }
}
