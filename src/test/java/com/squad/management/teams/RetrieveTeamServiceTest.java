package com.squad.management.teams;

import com.squad.management.exceptions.ResourceNotFoundException;
import com.squad.management.fixtures.TeamFactory;
import com.squad.management.fixtures.UserFactory;
import com.squad.management.users.RetrieveUserService;
import com.squad.management.users.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest(includeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {RetrieveTeamService.class, TeamRepository.class}))
public class RetrieveTeamServiceTest {

    @Autowired
    private RetrieveTeamService service;
    @Autowired
    private TeamRepository teamRepository;

    @BeforeEach
    void setUp() {
        teamRepository.deleteAll();
    }

    @Test
    @DisplayName("should list teams")
    void test1() {
        teamRepository.saveAll(List.of(TeamFactory.getTeamWithoutTeamLead()));
        var listTeamsResponses = service.listTeams();

        assertEquals(1, listTeamsResponses.size());
    }

    @Test
    @DisplayName("should throw resource not found exception given a non existing id")
    void test2() {
        assertThrows(ResourceNotFoundException.class, () -> service.getTeamById(1L));
    }

    @Test
    @DisplayName("should throw resource not found exception given a non existing id")
    void test3() {
        var team = teamRepository.save(TeamFactory.getTeamWithoutTeamLead());
        var teamResponse = service.getTeamById(team.getId());

        assertEquals(teamResponse.id(), team.getId());
        assertEquals(teamResponse.name(), team.getName());
    }
}
