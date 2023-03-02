package com.squad.management.roles;

import com.squad.management.exceptions.ResourceNotFoundException;
import com.squad.management.memberships.MembershipRepository;
import com.squad.management.roles.dto.CreateRoleRequest;
import com.squad.management.teams.TeamRepository;
import com.squad.management.users.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest(includeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {CreateRoleService.class, RoleRepository.class}))
public class CreateRoleServiceTest {

    @Autowired
    private CreateRoleService service;
    @Autowired
    private RoleRepository roleRepository;

    @BeforeEach
    void setUp() {
        roleRepository.deleteAll();
    }

    @Test
    @DisplayName("should thrown exception when creating a role that already exists")
    void test1() {
        roleRepository.save(new Role(1L, "Developer"));
        assertThrows(IllegalStateException.class, () -> service.createRole(new CreateRoleRequest("Developer")));
    }

    @Test
    @DisplayName("should create a role given a valid name")
    void test2() {
        var role = service.createRole(new CreateRoleRequest("Developer"));

        assertEquals("Developer", role.getName());
        assertNotNull(role.getId());
    }
}
