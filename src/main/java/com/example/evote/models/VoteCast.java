package com.example.evote.models;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class VoteCast {
    @EmbeddedId
    VoteCastId id;

    @ManyToOne
    @MapsId("voterId")
    @JoinColumn(name = "voter_id", nullable = false)
    private Voter voter;

    @ManyToOne
    @MapsId("electionId")
    @JoinColumn(name = "election_id", nullable = false)
    private Election election;

    @ManyToOne
    @JoinColumn(name = "candidate_id", nullable = false)
    private Candidate candidate;

    private LocalDate timeStamp;

    protected VoteCast() {
    }

    public VoteCast(VoteCastId id, LocalDate timeStamp) {
        this.id = id;
        this.timeStamp = timeStamp;
    }

    public VoteCastId getId() {
        return this.id;
    }

    public Voter getStudent() {
        return this.voter;
    }

    public Election getElection() {
        return this.election;
    }

    public void setElection(Election election) {
        this.election = election;
    }

    public Candidate getCandidate() {
        return this.candidate;
    }

    public void setCandidate(Candidate candidate) {
        this.candidate = candidate;
    }

    public void setVoter(Voter voter) {
        this.voter = voter;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        VoteCast other = (VoteCast) obj;
        return this.id.equals(other.id);
    }

    @Override
    public int hashCode() {
        return this.id.hashCode();
    }

    public LocalDate getTimeStamp() {
        return this.timeStamp;
    }
}
