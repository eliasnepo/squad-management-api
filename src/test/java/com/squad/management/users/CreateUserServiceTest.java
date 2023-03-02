package com.squad.management.users;

import com.squad.management.roles.CreateRoleService;
import com.squad.management.roles.Role;
import com.squad.management.roles.RoleRepository;
import com.squad.management.roles.dto.CreateRoleRequest;
import com.squad.management.users.dto.CreateUserRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.web.bind.MethodArgumentNotValidException;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest(includeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {CreateUserService.class, UserRepository.class}))
public class CreateUserServiceTest {

    @Autowired
    private CreateUserService service;
    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("should create an user given a valid data")
    void test2() {
        var user = service.createUser(new CreateUserRequest("Elias", "Nepomuceno", "enepo", "profile.png", "Brazil"));

        assertNotNull(user.getId());
    }
}
