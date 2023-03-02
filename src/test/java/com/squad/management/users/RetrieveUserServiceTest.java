package com.squad.management.users;

import com.squad.management.exceptions.ResourceNotFoundException;
import com.squad.management.fixtures.UserFactory;
import com.squad.management.users.dto.ListUsersResponse;
import com.squad.management.users.dto.UserResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest(includeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {RetrieveUserService.class, UserRepository.class}))
public class RetrieveUserServiceTest {

    @Autowired
    private RetrieveUserService service;
    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("should list users")
    void test1() {
        userRepository.saveAll(List.of(UserFactory.getUserWithoutId(), UserFactory.getUserWithoutId()));
        var listUsersResponses = service.listUsers();

        assertEquals(2, listUsersResponses.size());
    }

    @Test
    @DisplayName("should throw resource not found exception given a non existing id")
    void test2() {
        assertThrows(ResourceNotFoundException.class, () -> service.getUserById(1L));
    }

    @Test
    @DisplayName("should throw resource not found exception given a non existing id")
    void test3() {
        var user = userRepository.save(UserFactory.getUserWithoutId());
        var userResponse = service.getUserById(user.getId());

        assertEquals(userResponse.id(), user.getId());
        assertEquals(userResponse.firstName(), user.getFirstName());
        assertEquals(userResponse.lastName(), user.getLastName());
        assertEquals(userResponse.displayName(), user.getDisplayName());
        assertEquals(userResponse.avatarUrl(), user.getAvatarUrl());
        assertEquals(userResponse.location(), user.getLocation());
    }
}
