package com.example.evote.interfaces;

import com.example.evote.models.VoteCast;
import com.example.evote.models.VoteCastId;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;

public interface VoteCastRepository extends CrudRepository<VoteCast, VoteCastId> {
    @Query(value = "select * from vote_cast where voter_id = :voterId", nativeQuery = true)
    ArrayList<VoteCast> findByVoterId(@Param("voterId") Long voterId);

    @Query(value = "select count(candidate_id) from vote_cast where candidate_id = :candidateId", nativeQuery = true)
    Integer getCandidateVoteTally(@Param("candidateId") Long candidateId);
}
