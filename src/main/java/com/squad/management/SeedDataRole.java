package com.squad.management;

import com.squad.management.roles.Role;
import com.squad.management.roles.RoleRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.util.stream.Stream;

@Component
public class SeedDataRole {

    private final RoleRepository roleRepository;

    public SeedDataRole(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @PostConstruct
    private void init() {
        Stream
            .of(
                new Role(1L, "Developer"),
                new Role(2L, "Product Owner"),
                new Role(3L, "Tester"))
            .forEach(role ->
                roleRepository
                    .findById(role.getId())
                    .orElse(roleRepository.save(role))
        );
    }
}
