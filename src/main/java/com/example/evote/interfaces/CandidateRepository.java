package com.example.evote.interfaces;

import com.example.evote.models.Candidate;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;

public interface CandidateRepository extends CrudRepository<Candidate, Long> {
    @Query(value = "select * from candidate where first_name = :firstName and last_name = :lastName", nativeQuery = true)
    Candidate findByName(@Param("firstName") String firstName, @Param("lastName") String lastName);

    @Query(value = "select * from candidate join election on candidate.election_id = election.id where election_id = :electionId", nativeQuery = true)
    ArrayList<Candidate> findByElectionId(@Param("electionId") Long electionId);
}
