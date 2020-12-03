package com.example.evote.requests;

public class CastVoteRequest {
    private final Long electionId;
    private final Long candidateId;
    private final Long voterId;

    public CastVoteRequest(Long electionId, Long candidateId, Long voterId) {
        this.electionId = electionId;
        this.candidateId = candidateId;
        this.voterId = voterId;
    }

    public Long getElectionId() {
        return this.electionId;
    }

    public Long getCandidateId() {
        return this.candidateId;
    }

    public Long getVoterId() {
        return this.voterId;
    }

}
