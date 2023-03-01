package com.squad.management.teams;

import com.squad.management.teams.dto.CreateTeamRequest;
import com.squad.management.teams.dto.ListTeamsResponse;
import com.squad.management.teams.dto.TeamResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/teams")
public class TeamController {

    private final RetrieveTeamService retrieveTeamService;
    private final AssignUserService assignUserService;
    private final CreateTeamUserService createTeamUserService;

    public TeamController(RetrieveTeamService retrieveTeamService, AssignUserService assignUserService, CreateTeamUserService createTeamUserService) {
        this.retrieveTeamService = retrieveTeamService;
        this.assignUserService = assignUserService;
        this.createTeamUserService = createTeamUserService;
    }

    @GetMapping
    public List<ListTeamsResponse> listTeams() {
        return retrieveTeamService.listTeams();
    }

    @GetMapping("/{id}")
    public TeamResponse getTeamById(@PathVariable Long id) {
        return retrieveTeamService.getTeamById(id);
    }

    @PostMapping("/{teamId}/assign-user/{userId}")
    public void assignUser(@PathVariable Long teamId, @PathVariable Long userId) {
        assignUserService.assignUserToTeam(teamId, userId);
    }

    @PostMapping
    public ResponseEntity<TeamResponse> createTeam(@Valid @RequestBody CreateTeamRequest request) {
        var team = createTeamUserService.createTeam(request);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(team.getId())
                .toUri();

        return ResponseEntity.created(uri).body(TeamResponse.fromDomain(team));
    }

}
