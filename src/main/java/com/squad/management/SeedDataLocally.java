package com.squad.management;

import com.squad.management.teams.Team;
import com.squad.management.teams.TeamRepository;
import com.squad.management.users.User;
import com.squad.management.users.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.stream.Stream;

@Component
@ConditionalOnProperty(name = "spring.profiles.active", havingValue = "local")
public class SeedDataLocally {

    private final UserRepository userRepository;
    private final TeamRepository teamRepository;

    public SeedDataLocally(UserRepository userRepository, TeamRepository teamRepository) {
        this.userRepository = userRepository;
        this.teamRepository = teamRepository;
    }

    @PostConstruct
    private void init() {
        /* Seed Data for users */
        Stream.of(
                new User(null, "John", "Doe", "jdoe", "https://github.com/profile.png", "United States"),
                new User(null, "Maria", "Souza", "msouza", "https://github.com/profile.png", "Brazil"),
                new User(null, "Elias", "Souza", "esouza", "https://github.com/profile.png", "Brazil"),
                new User(null, "Joey", "Tribbiani", "jtrib", "https://github.com/profile.png", "United States"),
                new User(null, "Ross", "Geller", "rgeller", "https://github.com/profile.png", "United States"),
                new User(null, "Monica", "Geller", "mgeller", "https://github.com/profile.png", "United States"),
                new User(null, "Rachel", "Green", "rgreen", "https://github.com/profile.png", "United States")
        ).forEach(userRepository::save);

        /* Seed Data for teams */
        Stream.of(
                new Team(null, "Product A", null),
                new Team(null, "Product B", null),
                new Team(null, "Product C", null),
                new Team(null, "Product D", null),
                new Team(null, "Product E", null)
        ).forEach(team -> {
                    var optTeam = teamRepository.findByName(team.getName());
                    if (optTeam.isEmpty()) {
                        teamRepository.save(team);
                    }
                }
        );
    }
}
