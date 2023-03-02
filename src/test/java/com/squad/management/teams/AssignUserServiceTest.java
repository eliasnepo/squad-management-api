package com.squad.management.teams;

import com.squad.management.exceptions.ResourceNotFoundException;
import com.squad.management.fixtures.TeamFactory;
import com.squad.management.fixtures.UserFactory;
import com.squad.management.memberships.Membership;
import com.squad.management.memberships.MembershipId;
import com.squad.management.memberships.MembershipRepository;
import com.squad.management.users.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest(includeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {AssignUserService.class, TeamRepository.class, UserRepository.class, MembershipRepository.class}))
public class AssignUserServiceTest {

    @Autowired
    private AssignUserService service;
    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MembershipRepository membershipRepository;

    @BeforeEach
    void setUp() {
        membershipRepository.deleteAll();
        userRepository.deleteAll();
        teamRepository.deleteAll();
    }

    @Test
    @DisplayName("should throw exception given a invalid team id")
    void test1() {
        var user = userRepository.save(UserFactory.getUserWithoutId());
        assertThrows(ResourceNotFoundException.class, () -> service.assignUserToTeam(Long.MAX_VALUE, user.getId()));
    }

    @Test
    @DisplayName("should throw exception given a invalid user id")
    void test2() {
        var team = teamRepository.save(TeamFactory.getTeamWithoutTeamLead());
        assertThrows(ResourceNotFoundException.class, () -> service.assignUserToTeam(team.getId(), Long.MAX_VALUE));
    }

    @Test
    @DisplayName("should assign user to a team with developer as the default role")
    void test3() {
        var user = userRepository.save(UserFactory.getUserWithoutId());
        var team = teamRepository.save(TeamFactory.getTeamWithoutTeamLead());
        service.assignUserToTeam(team.getId(), user.getId());

        assertEquals(membershipRepository.count(), 1l);
    }
}
