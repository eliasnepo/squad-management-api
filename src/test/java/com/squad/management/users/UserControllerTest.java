package com.squad.management.users;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.squad.management.fixtures.UserFactory;
import com.squad.management.users.dto.CreateUserRequest;
import com.squad.management.users.dto.ListUsersResponse;
import com.squad.management.users.dto.UserResponse;
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

@WebMvcTest(controllers = UserController.class)
@AutoConfigureMockMvc(addFilters = false, printOnlyOnFailure = false)
public class UserControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    CreateUserService createUserService;

    @MockBean
    RetrieveUserService retrieveUserService;

    @Test
    @DisplayName("should return list of users")
    void test1() throws Exception {
        var expectedResponse = List.of(ListUsersResponse.fromDomain(UserFactory.getUser()), ListUsersResponse.fromDomain(UserFactory.getUser()));
        when(retrieveUserService.listUsers())
                .thenReturn(expectedResponse);

        var request = get("/users").contentType(MediaType.APPLICATION_JSON);

        var response = mvc.perform(request)
                .andExpect(
                        status().isOk()
                )
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

        assertEquals(response, mapper.writeValueAsString(expectedResponse));

        verify(retrieveUserService, times(1)).listUsers();
    }

    @Test
    @DisplayName("should return a specific user")
    void test2() throws Exception {
        var expectedResponse = UserResponse.fromDomain(UserFactory.getUser());
        when(retrieveUserService.getUserById(UserFactory.getUser().getId()))
                .thenReturn(expectedResponse);

        var request = get("/users/{id}", UserFactory.getUser().getId()).contentType(MediaType.APPLICATION_JSON);

        var response = mvc.perform(request)
                .andExpect(
                        status().isOk()
                )
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

        assertEquals(response, mapper.writeValueAsString(expectedResponse));

        verify(retrieveUserService, times(1)).getUserById(UserFactory.getUser().getId());
    }

    @Test
    @DisplayName("should create a user given a valid data")
    void test3() throws Exception {
        var expectedResponse = UserFactory.getUser();
        var userRequest = new CreateUserRequest("Elias", "Nepomuceno", "enepo", "profile.png", "Brazil");

        when(createUserService.createUser(userRequest))
                .thenReturn(expectedResponse);

        var request = post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(userRequest));

        var response = mvc.perform(request)
                .andExpect(
                        status().isCreated()
                )
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

        assertEquals(response, mapper.writeValueAsString(expectedResponse));

        verify(createUserService, times(1)).createUser(userRequest);
    }

    @Test
    @DisplayName("should thrown jakarta exception given a invalid request")
    void test4() throws Exception {
        var expectedResponse = UserFactory.getUser();
        var userRequest = new CreateUserRequest("", "", "enepo", "profile.png", "Brazil");

        when(createUserService.createUser(userRequest))
                .thenReturn(expectedResponse);

        var request = post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(userRequest));

        Exception resolvedException = mvc.perform(request)
                .andExpect(
                        status().isBadRequest()
                )
                .andReturn()
                .getResolvedException();

        assertNotNull(resolvedException);
        assertEquals(MethodArgumentNotValidException.class, resolvedException.getClass());

        verify(createUserService, times(0)).createUser(userRequest);
    }
}
