package com.squad.management.teams;

import com.squad.management.memberships.Membership;
import com.squad.management.users.User;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "tb_teams")
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(unique = true, nullable = false)
    private String name;

    @ManyToOne
    private User teamLead;

    @OneToMany(mappedBy = "team", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Membership> memberships = new HashSet<>();

    /* Necessary because of JPA reflection */
    public Team() {}

    public Team(UUID id, String name, User teamLead, Set<Membership> memberships) {
        this.id = id;
        this.name = name;
        this.teamLead = teamLead;
        this.memberships = memberships;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public User getTeamLead() {
        return teamLead;
    }

    public Set<Membership> getMemberships() {
        return memberships;
    }
}
