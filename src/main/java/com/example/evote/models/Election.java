package com.example.evote.models;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Election {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String description;
    @OneToMany(mappedBy = "election")
    private Set<Candidate> candidates;
    @OneToMany(mappedBy = "election")
    private Set<VoteCast> votes;

    protected Election() {
    }

    public Election(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Long getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public Set<Candidate> getCandidates() {
        return this.candidates;
    }

    public void setCandidates(Set<Candidate> list) {
        candidates = list;
    }

    public Set<VoteCast> getVotes() {
        return this.votes;
    }

    public void setVotes(Set<VoteCast> votes) {
        this.votes = votes;
    }
}
