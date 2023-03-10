package com.squad.management.roles;

import com.squad.management.memberships.dto.MembershipResponse;
import com.squad.management.roles.dto.CreateRoleRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Set;

@RestController
@RequestMapping("/roles")
public class RoleController {

    private final CreateRoleService createRoleService;
    private final LookUpRoleService lookUpRoleService;
    private final AssignRoleService assignRoleService;

    public RoleController(CreateRoleService createRoleService, LookUpRoleService lookUpRoleService, AssignRoleService assignRoleService) {
        this.createRoleService = createRoleService;
        this.lookUpRoleService = lookUpRoleService;
        this.assignRoleService = assignRoleService;
    }

    @PostMapping
    public ResponseEntity<Role> createRole(@Valid @RequestBody CreateRoleRequest request) {
        var role = createRoleService.createRole(request);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(role.getId())
                .toUri();

        return ResponseEntity.created(uri).body(role);
    }

    @GetMapping("/team/{teamId}/user/{userId}")
    public String lookUpRoleForAMembership(@PathVariable Long teamId, @PathVariable Long userId) {
        return lookUpRoleService.lookUpRoleForAMembership(teamId, userId);
    }

    @GetMapping("/{roleId}/memberships")
    public Set<MembershipResponse> lookUpMembershipsForARole(@PathVariable Long roleId) {
        return lookUpRoleService.lookUpMembershipsForARole(roleId);
    }

    @PostMapping("/{roleId}/user/{userId}/team/{teamId}/assign")
    public MembershipResponse assignUser(@PathVariable Long roleId, @PathVariable Long userId, @PathVariable Long teamId) {
        return assignRoleService.assignRoleToUser(roleId, userId, teamId);
    }
}
