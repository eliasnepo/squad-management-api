package com.squad.management.teams;

import com.squad.management.fixtures.UserFactory;
import com.squad.management.memberships.MembershipRepository;
import com.squad.management.roles.Role;
import com.squad.management.roles.RoleRepository;
import com.squad.management.teams.dto.CreateTeamRequest;
import com.squad.management.users.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest(includeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {CreateTeamService.class, TeamRepository.class, RoleRepository.class, UserRepository.class, MembershipRepository.class}))
public class CreateTeamServiceTest {

    @Autowired
    private CreateTeamService service;
    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MembershipRepository membershipRepository;

    @BeforeEach
    void setUp() {
        membershipRepository.deleteAll();
        teamRepository.deleteAll();
        userRepository.deleteAll();
        roleRepository.deleteAll();
    }

    @Test
    @DisplayName("should create a team without a team lead as a membership")
    void test1() {
        var team = service.createTeam(new CreateTeamRequest("Product B", null));

        assertNotNull(team.getId());
        assertEquals(team.getName(), "Product B");
    }

    @Test
    @DisplayName("should create a team with a team lead as a membership")
    void test2() {
        roleRepository.save(new Role(1L, "Developer"));
        var teamLead = userRepository.save(UserFactory.getUserWithoutId());

        var team = service.createTeam(new CreateTeamRequest("Product B", teamLead.getId()));

        assertNotNull(team.getId());
        assertEquals(team.getName(), "Product B");
        assertEquals(team.getTeamLead().getId(), teamLead.getId());
        assertEquals(membershipRepository.count(), 1l);
    }
}
