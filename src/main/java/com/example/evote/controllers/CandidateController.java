package com.example.evote.controllers;

import com.example.evote.interfaces.CandidateRepository;
import com.example.evote.models.Candidate;
import com.example.evote.requests.UpdateCandidateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class CandidateController {

    @Autowired
    CandidateRepository candidateRepository;

    @RequestMapping(value = "/updateCandidate", method = RequestMethod.POST, produces = "text/plain")
    public @ResponseBody
    ResponseEntity<String> UpdateCandidate(@RequestBody UpdateCandidateRequest request) {
        try {
            if (request == null) {
                throw new IllegalArgumentException();
            }
            Candidate candidate = candidateRepository.findById(request.getCandidateId()).get();
            if (candidate == null) {
                throw new NullPointerException("No candidate found with Id " + request.getCandidateId());
            }
            candidate.setFirstName(request.getFirstName());
            candidate.setLastName(request.getLastName());
            candidateRepository.save(candidate);
            return new ResponseEntity<String>("Candidate was updated successfully.", HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return new ResponseEntity<String>("Failed to update candidate", HttpStatus.BAD_REQUEST);
    }
}
