package com.squad.management.teams;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.squad.management.fixtures.TeamFactory;
import com.squad.management.fixtures.UserFactory;
import com.squad.management.teams.dto.CreateTeamRequest;
import com.squad.management.teams.dto.ListTeamsResponse;
import com.squad.management.teams.dto.TeamResponse;
import com.squad.management.users.dto.CreateUserRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

    @Test
    @DisplayName("should assign user to a team")
    void test3() throws Exception {

        doNothing().when(assignUserService).assignUserToTeam(1L, 1L);

        var request = post("/teams/1/assign-user/1")
                .contentType(MediaType.APPLICATION_JSON);

        var response = mvc.perform(request)
                .andExpect(
                        status().isOk()
                )
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

        assertEquals("", response);

        verify(assignUserService, times(1)).assignUserToTeam(1L, 1L);
    }

    @Test
    @DisplayName("should create a team given a valid data")
    void test4() throws Exception {
        var expectedResponse = TeamFactory.getTeamWithoutTeamLead();
        var teamRequest = new CreateTeamRequest(TeamFactory.getTeamWithoutTeamLead().getName(), null);

        when(createTeamService.createTeam(teamRequest))
                .thenReturn(expectedResponse);

        var request = post("/teams")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(teamRequest));

        var response = mvc.perform(request)
                .andExpect(
                        status().isCreated()
                )
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

        assertEquals(response, mapper.writeValueAsString(TeamResponse.fromDomain(expectedResponse)));

        verify(createTeamService, times(1)).createTeam(teamRequest);
    }

    @Test
    @DisplayName("should thrown jakarta exception given a invalid request")
    void test5() throws Exception {
        var expectedResponse = TeamFactory.getTeamWithoutTeamLead();
        var teamRequest = new CreateTeamRequest("", null);

        when(createTeamService.createTeam(teamRequest))
                .thenReturn(expectedResponse);

        var request = post("/teams")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(teamRequest));

        Exception resolvedException = mvc.perform(request)
                .andExpect(
                        status().isBadRequest()
                )
                .andReturn()
                .getResolvedException();

        assertNotNull(resolvedException);
        assertEquals(MethodArgumentNotValidException.class, resolvedException.getClass());

        verify(createTeamService, times(0)).createTeam(teamRequest);
    }

}
