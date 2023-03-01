package com.squad.management;

import com.squad.management.memberships.Membership;
import com.squad.management.roles.Role;
import com.squad.management.teams.Team;
import com.squad.management.teams.TeamRepository;
import com.squad.management.users.User;
import com.squad.management.users.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

@Component
@DependsOn("seedDataRole")
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
                new User(1L, "John", "Doe", "jdoe", "https://github.com/profile.png", "United States"),
                new User(2L, "Maria", "Souza", "msouza", "https://github.com/profile.png", "Brazil"),
                new User(3L, "Elias", "Souza", "esouza", "https://github.com/profile.png", "Brazil"),
                new User(4L, "Joey", "Tribbiani", "jtrib", "https://github.com/profile.png", "United States"),
                new User(5L, "Ross", "Geller", "rgeller", "https://github.com/profile.png", "United States"),
                new User(6L, "Monica", "Geller", "mgeller", "https://github.com/profile.png", "United States"),
                new User(7L, "Rachel", "Green", "rgreen", "https://github.com/profile.png", "United States")
        ).forEach(user -> {
            if (!userRepository.existsById(user.getId())) {
                userRepository.save(user);
            }
        });

        /* Seed Data for teams */
        Stream.of(
                new Team(1L, "Product A", null),
                new Team(2L, "Product B", new User(3L)),
                new Team(3L, "Product C", null),
                new Team(4L, "Product D", new User(6L)),
                new Team(5L, "Product E", new User(7L))
        ).forEach(team -> {
            if (team.getTeamLead() != null) {
                team.addMembership(new Membership(team.getTeamLead(), team, new Role(1L)));
            }

            if (!teamRepository.existsById(team.getId())) {
                teamRepository.save(team);
            }
        });
    }
}
