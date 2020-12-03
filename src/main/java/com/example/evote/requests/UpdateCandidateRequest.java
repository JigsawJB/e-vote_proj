package com.example.evote.requests;

public class UpdateCandidateRequest {
    private final String firstName;
    private final String lastName;
    private final Long candidateId;

    public UpdateCandidateRequest(String firstName, String lastName, Long candidateId) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.candidateId = candidateId;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public Long getCandidateId() {
        return this.candidateId;
    }
}
