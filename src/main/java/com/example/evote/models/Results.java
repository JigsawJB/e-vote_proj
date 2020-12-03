package com.example.evote.models;

public class Results {
    private final String firstName;
    private final String lastName;
    private final String electionName;
    private final Integer voteTally;
    private final Long candidateId;

    public Results(String firstName, String lastName, String electionName, Integer voteTally, Long candidateId) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.electionName = electionName;
        this.voteTally = voteTally;
        this.candidateId = candidateId;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public String getElectionName() {
        return this.electionName;
    }

    public Integer getVoteTally() {
        return this.voteTally;
    }

    public Long getCandidateId() {
        return this.candidateId;
    }
}
