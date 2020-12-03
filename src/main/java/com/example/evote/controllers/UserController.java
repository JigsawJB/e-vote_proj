package com.example.evote.controllers;

import com.example.evote.interfaces.CandidateRepository;
import com.example.evote.interfaces.VoteCastRepository;
import com.example.evote.interfaces.VoterRepository;
import com.example.evote.models.*;
import com.example.evote.requests.Login;
import com.example.evote.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;

@Controller
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    VoterRepository voterRepository;

    @Autowired
    VoteCastRepository voteCastRepository;

    @Autowired
    CandidateRepository candidateRepository;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String Login() {
        try {
            return "login";
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return "error";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST, produces = "text/plain")
    public @ResponseBody
    ResponseEntity<String> Login(@RequestBody Login request) {
        try {
            if (request == null) {
                throw new IllegalArgumentException("Request is null.");
            }
            if (request.getIsAdmin()) {
                Admin admin = userService.getAdminUser(request.getUserName());
                if (admin == null) {
                    return new ResponseEntity<String>("Invalid username or password.", HttpStatus.BAD_REQUEST);
                }
                String requestHashedPassword = userService.getHashedPassword(request.getPassword(), admin.getPasswordSalt());
                if (!requestHashedPassword.equals(admin.getHashPassword())) {
                    return new ResponseEntity<String>("Invalid username or password.", HttpStatus.BAD_REQUEST);
                }
                return new ResponseEntity<>("/dashboard", HttpStatus.OK);
            } else {
                Voter voter = userService.getVoter(Long.parseLong(request.getUserName()));
                if (voter == null) {
                    return new ResponseEntity<String>("Invalid username or password.", HttpStatus.BAD_REQUEST);
                }
                String requestHashedPassword = userService.getHashedPassword(request.getPassword(), voter.getPasswordSalt());
                if (!requestHashedPassword.equals(voter.getHashPassword())) {
                    return new ResponseEntity<String>("Invalid username or password.", HttpStatus.BAD_REQUEST);
                }
                return new ResponseEntity<String>("/" + voter.getSessionToken() + "/vote", HttpStatus.OK);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return new ResponseEntity<String>("Login failed.", HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public String AdminLogin() {
        try {
            return "adminLogin";
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return "error";
    }

    @RequestMapping(value = "/dashboard", method = RequestMethod.GET)
    public String AdminDashboard(ModelMap model) {
        try {
            ArrayList<VoterView> voterViews = new ArrayList<VoterView>();
            Iterable<Voter> voters = voterRepository.findAll();
            voters.forEach(voter -> {
                Boolean hasVoted = !voteCastRepository.findByVoterId(voter.getVoterId()).isEmpty();
                voterViews.add(new VoterView(voter.getFirstName(), voter.getLastName(), hasVoted));
            });
            model.addAttribute("voters", voterViews);
            return "adminDashboard";
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return "error";
    }

    @RequestMapping(value = "/candidatelist", method = RequestMethod.GET)
    public String CandidateList(ModelMap model) {
        try {
            ArrayList<Results> candidateViews = new ArrayList<Results>();
            Iterable<Candidate> candidates = candidateRepository.findAll();
            candidates.forEach(candidate -> {
                Integer voteTally = voteCastRepository.getCandidateVoteTally(candidate.getId());
                candidateViews.add(new Results(candidate.getFirstName(), candidate.getLastName(), candidate.getElection().getName(), voteTally, candidate.getId()));
            });
            model.addAttribute("candidates", candidateViews);
            return "candidate-list";
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return "error";
    }
}
