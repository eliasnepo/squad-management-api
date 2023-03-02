package com.squad.management.teams;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.squad.management.fixtures.TeamFactory;
import com.squad.management.fixtures.UserFactory;
import com.squad.management.teams.dto.ListTeamsResponse;
import com.squad.management.teams.dto.TeamResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = TeamController.class)
@AutoConfigureMockMvc(addFilters = false, printOnlyOnFailure = false)
public class TeamControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    RetrieveTeamService retrieveTeamService;

    @MockBean
    AssignUserService assignUserService;

    @MockBean
    CreateTeamService createTeamService;

    @Test
    @DisplayName("should return list of teams")
    void test1() throws Exception {
        var expectedResponse = List.of(ListTeamsResponse.fromDomain(TeamFactory.getTeamWithoutTeamLead()));
        when(retrieveTeamService.listTeams())
                .thenReturn(expectedResponse);

        var request = get("/teams").contentType(MediaType.APPLICATION_JSON);

        var response = mvc.perform(request)
                .andExpect(
                        status().isOk()
                )
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

        assertEquals(response, mapper.writeValueAsString(expectedResponse));

        verify(retrieveTeamService, times(1)).listTeams();
    }

    @Test
    @DisplayName("should return a specific team")
    void test2() throws Exception {
        var expectedResponse = TeamResponse.fromDomain(TeamFactory.getTeamWithoutTeamLead());
        when(retrieveTeamService.getTeamById(TeamFactory.getTeamWithoutTeamLead().getId()))
                .thenReturn(expectedResponse);

        var request = get("/teams/{id}", UserFactory.getUser().getId()).contentType(MediaType.APPLICATION_JSON);

        var response = mvc.perform(request)
                .andExpect(
                        status().isOk()
                )
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

        assertEquals(response, mapper.writeValueAsString(expectedResponse));

        verify(retrieveTeamService, times(1)).getTeamById(TeamFactory.getTeamWithoutTeamLead().getId());
    }
}
