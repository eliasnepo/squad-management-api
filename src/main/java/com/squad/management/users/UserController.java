package com.squad.management.users;

import com.squad.management.roles.Role;
import com.squad.management.users.dto.CreateUserRequest;
import com.squad.management.users.dto.ListUsersResponse;
import com.squad.management.users.dto.UserResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {

    private final RetrieveUserService retrieveUserService;
    private final CreateUserService createUserService;

    public UserController(RetrieveUserService retrieveUserService, CreateUserService createUserService) {
        this.retrieveUserService = retrieveUserService;
        this.createUserService = createUserService;
    }

    @GetMapping
    public List<ListUsersResponse> listUsers() {
        return retrieveUserService.listUsers();
    }

    @GetMapping("/{id}")
    public UserResponse getUserById(@PathVariable Long id) {
        return retrieveUserService.getUserById(id);
    }

    @PostMapping
    public ResponseEntity<UserResponse> createUser(@Valid @RequestBody CreateUserRequest request) {
        var user = createUserService.createUser(request);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(user.getId())
                .toUri();

        return ResponseEntity.created(uri).body(UserResponse.fromDomain(user));
    }
}
