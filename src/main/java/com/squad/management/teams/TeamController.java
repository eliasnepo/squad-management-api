package com.squad.management.teams;

import com.squad.management.teams.dto.ListTeamsResponse;
import com.squad.management.teams.dto.TeamResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/teams")
public class TeamController {

    private final RetrieveTeamService retrieveTeamService;

    public TeamController(RetrieveTeamService retrieveTeamService) {
        this.retrieveTeamService = retrieveTeamService;
    }

    @GetMapping
    public List<ListTeamsResponse> listTeams() {
        return retrieveTeamService.listTeams();
    }

    @GetMapping("/{id}")
    public TeamResponse getTeamById(@PathVariable Long id) {
        return retrieveTeamService.getTeamById(id);
    }
}
