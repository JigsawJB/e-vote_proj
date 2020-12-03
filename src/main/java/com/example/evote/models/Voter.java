package com.example.evote.models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.Set;

@Entity
public class Voter {
    @Id
    private Long voterId = 0L;
    private String firstName = "";
    private String lastName = "";
    private String hashPassword = "";
    private String passwordSalt = "";
    @OneToMany(mappedBy = "voter")
    private Set<VoteCast> votes;
    private String sessionToken = "";

    protected Voter() {
    }

    public Voter(Long voterId, String firstName, String lastName, String hashPassword, String passwordSalt, String sessionToken) {
        this.voterId = voterId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.hashPassword = hashPassword;
        this.passwordSalt = passwordSalt;
        this.sessionToken = sessionToken;
    }

    public Long getVoterId() {
        return this.voterId;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public String getHashPassword() {
        return this.hashPassword;
    }

    public Set<VoteCast> getVotes() {
        return this.votes;
    }

    public String getPasswordSalt() {
        return this.passwordSalt;
    }

    public String getSessionToken() {
        return this.sessionToken;
    }
}
