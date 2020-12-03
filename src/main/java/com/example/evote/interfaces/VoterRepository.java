package com.example.evote.interfaces;

import com.example.evote.models.Voter;
import org.springframework.data.repository.CrudRepository;

public interface VoterRepository extends CrudRepository<Voter, Long> {
    Voter findByVoterId(Long voterId);

    Voter findBySessionToken(String sessionToken);
}
