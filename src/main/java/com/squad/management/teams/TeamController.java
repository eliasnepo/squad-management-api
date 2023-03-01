package com.squad.management.teams;

import com.squad.management.teams.dto.ListTeamsResponse;
import com.squad.management.teams.dto.TeamResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/teams")
public class TeamController {

    private final RetrieveTeamService retrieveTeamService;
    private final AssignUserService assignUserService;

    public TeamController(RetrieveTeamService retrieveTeamService, AssignUserService assignUserService) {
        this.retrieveTeamService = retrieveTeamService;
        this.assignUserService = assignUserService;
    }

    @GetMapping
    public List<ListTeamsResponse> listTeams() {
        return retrieveTeamService.listTeams();
    }

    @GetMapping("/{id}")
    public TeamResponse getTeamById(@PathVariable Long id) {
        return retrieveTeamService.getTeamById(id);
    }

    @PostMapping("/{id}/assign-user/{userId}")
    public void assignUser(@PathVariable Long id, @PathVariable Long userId) {
        assignUserService.assignUserToTeam(id, userId);
    }
}
