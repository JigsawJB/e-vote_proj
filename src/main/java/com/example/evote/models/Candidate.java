package com.example.evote.models;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Candidate {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String firstName;
    private String lastName;
    @OneToMany(mappedBy = "candidate")
    private Set<VoteCast> votes;
    @ManyToOne
    @JoinColumn(name = "election_id", nullable = false)
    private Election election;

    protected Candidate() {
    }

    public Candidate(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Long getId() {
        return this.id;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Set<VoteCast> getVotes() {
        return this.votes;
    }

    public void setVotes(Set<VoteCast> votes) {
        this.votes = votes;
    }

    public Election getElection() {
        return this.election;
    }

    public void setElection(Election election) {
        this.election = election;
    }

    public Long getElectionId() {
        return getElection().getId();
    }

}
