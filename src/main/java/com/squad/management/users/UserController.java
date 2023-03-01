package com.squad.management.users;

import com.squad.management.users.dto.ListUsersResponse;
import com.squad.management.users.dto.UserResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final RetrieveUserService retrieveUserService;

    public UserController(RetrieveUserService retrieveUserService) {
        this.retrieveUserService = retrieveUserService;
    }

    @GetMapping
    public List<ListUsersResponse> listUsers() {
        return retrieveUserService.listUsers();
    }

    @GetMapping("/{id}")
    public UserResponse getUserById(@PathVariable Long id) {
        return retrieveUserService.getUserById(id);
    }
}
