package com.squad.management.teams;

import com.squad.management.exceptions.ResourceNotFoundException;
import com.squad.management.teams.dto.ListTeamsResponse;
import com.squad.management.teams.dto.TeamResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class RetrieveTeamService {

    private final TeamRepository repository;

    public RetrieveTeamService(TeamRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public List<ListTeamsResponse> listTeams() {
        return repository
                .findAll()
                .stream()
                .map(ListTeamsResponse::fromDomain)
                .toList();
    }

    @Transactional(readOnly = true)
    public TeamResponse getTeamById(UUID id) {
        return repository
                .findById(id)
                .map(TeamResponse::fromDomain)
                .orElseThrow(() -> new ResourceNotFoundException("Team not found with id " + id));
    }
}
