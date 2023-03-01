package com.squad.management.roles;

import com.squad.management.roles.dto.CreateRoleRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CreateRoleService {

    private final RoleRepository roleRepository;

    public CreateRoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Transactional
    public Role createRole(CreateRoleRequest request) {
        if (roleRepository.existsByName(request.name())) {
            throw new IllegalStateException("You can not create a role that already exists");
        }
        return roleRepository.save(new Role(null, request.name()));
    }
}
