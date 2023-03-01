package com.squad.management.teams;

import com.squad.management.exceptions.ResourceNotFoundException;
import com.squad.management.teams.dto.ListTeamsResponse;
import com.squad.management.teams.dto.TeamResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RetrieveTeamService {

    private final TeamRepository repository;

    public RetrieveTeamService(TeamRepository repository) {
        this.repository = repository;
    }

    public List<ListTeamsResponse> listTeams() {
        return repository
                .findAll()
                .stream()
                .map(ListTeamsResponse::fromDomain)
                .toList();
    }

    public TeamResponse getTeamById(Long id) {
        return repository
                .findById(id)
                .map(TeamResponse::fromDomain)
                .orElseThrow(() -> new ResourceNotFoundException("Team not found with id " + id));
    }
}
