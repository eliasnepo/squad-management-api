package com.squad.management.users;

import com.squad.management.exceptions.ResourceNotFoundException;
import com.squad.management.users.dto.ListUsersResponse;
import com.squad.management.users.dto.UserResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RetrieveUserService {

    private final UserRepository repository;

    public RetrieveUserService(UserRepository repository) {
        this.repository = repository;
    }

    public List<ListUsersResponse> listUsers() {
        return repository
                .findAll()
                .stream()
                .map(ListUsersResponse::fromDomain)
                .toList();
    }

    public UserResponse getUserById(Long id) {
        return repository
                .findById(id)
                .map(UserResponse::fromDomain)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + id));
    }
}
