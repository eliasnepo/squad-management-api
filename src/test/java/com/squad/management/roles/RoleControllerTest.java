package com.squad.management.roles;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.squad.management.memberships.dto.MembershipResponse;
import com.squad.management.roles.dto.CreateRoleRequest;
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
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = RoleController.class)
@AutoConfigureMockMvc(addFilters = false, printOnlyOnFailure = false)
public class RoleControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    AssignRoleService assignRoleService;

    @MockBean
    CreateRoleService createRoleService;

    @MockBean
    LookUpRoleService lookUpRoleService;

    @Test
    @DisplayName("should return a role given a valid membership")
    void test1() throws Exception {
        var expectedResponse = "Developer";
        when(lookUpRoleService.lookUpRoleForAMembership(1L, 1L))
                .thenReturn(expectedResponse);

        var request = get("/roles/team/1/user/1").contentType(MediaType.APPLICATION_JSON);

        var response = mvc.perform(request)
                .andExpect(
                        status().isOk()
                )
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

        assertEquals(response, expectedResponse);

        verify(lookUpRoleService, times(1)).lookUpRoleForAMembership(1L, 1L);
    }

    @Test
    @DisplayName("should return list of membership given a role")
    void test2() throws Exception {
        var expectedResponse = Set.of(new MembershipResponse("Elias Nepomuceno", "Product A", "Developer"));
        when(lookUpRoleService.lookUpMembershipsForARole(1L))
                .thenReturn(expectedResponse);

        var request = get("/roles/1/memberships").contentType(MediaType.APPLICATION_JSON);

        var response = mvc.perform(request)
                .andExpect(
                        status().isOk()
                )
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

        assertEquals(response, mapper.writeValueAsString(expectedResponse));

        verify(lookUpRoleService, times(1)).lookUpMembershipsForARole(1L);
    }

    @Test
    @DisplayName("should create a role given a valid data")
    void test3() throws Exception {
        var expectedResponse = new Role(1L, "Developer");
        var roleRequest = new CreateRoleRequest("Developer");
        when(createRoleService.createRole(roleRequest))
                .thenReturn(expectedResponse);

        var request = post("/roles")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(roleRequest));

        var response = mvc.perform(request)
                .andExpect(
                        status().isCreated()
                )
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

        assertEquals(response, mapper.writeValueAsString(expectedResponse));

        verify(createRoleService, times(1)).createRole(roleRequest);
    }

    @Test
    @DisplayName("should thrown jakarta exception given a invalid request")
    void test4() throws Exception {
        var expectedResponse = new Role(1L, "");
        var roleRequest = new CreateRoleRequest("");

        when(createRoleService.createRole(roleRequest))
                .thenReturn(expectedResponse);

        var request = post("/roles")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(roleRequest));

        Exception resolvedException = mvc.perform(request)
                .andExpect(
                        status().isBadRequest()
                )
                .andReturn()
                .getResolvedException();

        assertNotNull(resolvedException);
        assertEquals(MethodArgumentNotValidException.class, resolvedException.getClass());

        verify(createRoleService, times(0)).createRole(roleRequest);
    }

    @Test
    @DisplayName("should assign role to an user")
    void test5() throws Exception {
        var expectedResponse = new MembershipResponse("Elias Nepomuceno", "Product A", "Developer");

        when(assignRoleService.assignRoleToUser(1L, 1L, 1L))
                .thenReturn(expectedResponse);

        var request = post("/roles/1/user/1/team/1/assign")
                .contentType(MediaType.APPLICATION_JSON);

        var response = mvc.perform(request)
                .andExpect(
                        status().isOk()
                )
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

        assertEquals(response, mapper.writeValueAsString(expectedResponse));

        verify(assignRoleService, times(1)).assignRoleToUser(1L, 1L, 1L);
    }
}
