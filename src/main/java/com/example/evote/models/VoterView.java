package com.example.evote.models;

public class VoterView {
    private final String firstName;
    private final String lastName;
    private final Boolean hasVoted;

    public VoterView(String firstName, String lastName, Boolean hasVoted) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.hasVoted = hasVoted;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public Boolean getHasVoted() {
        return this.hasVoted;
    }
}
