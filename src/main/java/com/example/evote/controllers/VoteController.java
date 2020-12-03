package com.example.evote.controllers;

import com.example.evote.interfaces.CandidateRepository;
import com.example.evote.interfaces.ElectionRepository;
import com.example.evote.interfaces.VoteCastRepository;
import com.example.evote.interfaces.VoterRepository;
import com.example.evote.models.*;
import com.example.evote.requests.CastVoteRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

@Controller
public class VoteController {

    @Autowired
    CandidateRepository candidateRepository;

    @Autowired
    VoteCastRepository voteCastRepository;

    @Autowired
    ElectionRepository electionRepository;

    @Autowired
    VoterRepository voterRepository;

    @RequestMapping(value = "/{sessionId}/vote", method = RequestMethod.GET)
    public String CastVote(@PathVariable(name = "sessionId") String sessionId, ModelMap model) {
        try {
            Voter voter = voterRepository.findBySessionToken(sessionId);
            if (voter == null) {
                throw new NullPointerException("No voter found with the session token : " + sessionId);
            }
            model.addAttribute("voterId", voter.getVoterId());
            Iterable<Election> elections = electionRepository.findAll();
            ArrayList<VoteCast> voteCasts = voteCastRepository.findByVoterId(voter.getVoterId());
            for (Iterator<Election> election = elections.iterator(); election.hasNext(); ) {
                VoteCast voteCast = new VoteCast(new VoteCastId(voter.getVoterId(), election.next().getId()), LocalDate.now());
                if (voteCasts.contains(voteCast)) {
                    election.remove();
                }
            }
            model.addAttribute("elections", elections);
            return "vote";
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return "error";
    }

    @RequestMapping(value = "/vote", method = RequestMethod.POST, produces = "text/plain")
    public @ResponseBody
    ResponseEntity<String> SubmitVote(@RequestBody CastVoteRequest request) {
        try {
            if (request == null) {
                throw new IllegalArgumentException();
            }
            Candidate candidate = candidateRepository.findById(request.getCandidateId()).get();
            if (candidate == null) {
                throw new NullPointerException("No candidate found with Id " + request.getCandidateId());
            }
            Voter voter = voterRepository.findByVoterId(request.getVoterId());
            if (voter == null) {
                throw new NullPointerException("No voter found with Id " + request.getVoterId());
            }
            Election election = electionRepository.findById(request.getElectionId()).get();
            if (election == null) {
                throw new NullPointerException("No election found with Id " + request.getElectionId());
            }
            VoteCast voteCast = new VoteCast(new VoteCastId(request.getVoterId(), request.getCandidateId()), LocalDate.now());
            voteCast.setCandidate(candidate);
            voteCast.setVoter(voter);
            voteCast.setElection(election);
            voteCastRepository.save(voteCast);
            return new ResponseEntity<String>("success", HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return new ResponseEntity<String>("Failed to submit vote.", HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "/getCandidates/{electionId}")
    public @ResponseBody
    ArrayList<Candidate> GetCandidates(@PathVariable(name = "electionId") Long electionId) {
        try {
            ArrayList<Candidate> results = candidateRepository.findByElectionId(electionId);
            results.forEach(candidate -> {
                candidate.getElection().setCandidates(Set.of());
                candidate.getElection().setVotes(Set.of());
                candidate.setVotes(Set.of());
            });
            return results;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return new ArrayList<>();
    }
}
