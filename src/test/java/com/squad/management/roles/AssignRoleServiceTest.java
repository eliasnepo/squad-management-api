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

@DataJpaTest(includeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {AssignRoleService.class, RoleRepository.class, MembershipRepository.class, UserRepository.class, TeamRepository.class}))
public class AssignRoleServiceTest {

    @Autowired
    private AssignRoleService service;
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
    @DisplayName("should throw resource not found exception given a role id that does not exist")
    void test1() {
        assertThrows(ResourceNotFoundException.class, () -> service.assignRoleToUser(Long.MAX_VALUE, 1L, 1L));
    }

    @Test
    @DisplayName("should throw resource not found exception given a membership that does not exist")
    void test2() {
        assertThrows(ResourceNotFoundException.class, () -> {
            var role = roleRepository.save(new Role(null, "QA"));
            service.assignRoleToUser(role.getId(), 1L, 1L);
        });
    }

    @Test
    @DisplayName("should assign role given a role and membership that exists")
    void test3() {
        var user = userRepository.save(UserFactory.getUser());
        var role = roleRepository.save(new Role(null, "QA"));
        var team = teamRepository.save(TeamFactory.getTeamWithTeamLead(role));

        var membershipResponse = service.assignRoleToUser(role.getId(), user.getId(), team.getId());

        assertEquals(role.getName(), membershipResponse.role());
        assertEquals(team.getName(), membershipResponse.teamName());
        assertEquals(user.getFirstName() + " " + user.getLastName(), membershipResponse.teamMember());
    }
}
