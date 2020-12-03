package com.example.evote.models;

import java.io.Serializable;

public class VoteCastId implements Serializable {
    private Long voterId = 0L;
    private Long electionId = 0L;

    protected VoteCastId() {
    }

    public VoteCastId(Long voterId, Long electionId) {
        this.voterId = voterId;
        this.electionId = electionId;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        VoteCastId other = (VoteCastId) obj;
        return this.voterId.longValue() == other.voterId.longValue() && this.electionId.longValue() == other.electionId.longValue();
    }

    @Override
    public int hashCode() {
        return (int) (this.voterId.longValue() ^ this.electionId.longValue());
    }
}
