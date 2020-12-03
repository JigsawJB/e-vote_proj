package com.example.evote.services;

import com.example.evote.interfaces.AdminRepository;
import com.example.evote.interfaces.CandidateRepository;
import com.example.evote.interfaces.ElectionRepository;
import com.example.evote.interfaces.VoterRepository;
import com.example.evote.models.Admin;
import com.example.evote.models.Candidate;
import com.example.evote.models.Election;
import com.example.evote.models.Voter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.SecureRandom;
import java.security.spec.KeySpec;
import java.util.ArrayList;
import java.util.Base64;

@Component
@Order(0)
public class ApplicationInitializerService implements ApplicationListener<ApplicationReadyEvent> {
    @Autowired
    CandidateRepository candidateRepository;

    @Autowired
    VoterRepository voterRepository;

    @Autowired
    AdminRepository adminRepository;

    @Autowired
    ElectionRepository electionRepository;

    @Autowired
    UserService userService;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        try {
            SeedVoters();
            SeedElections();
            SeedAdmins();
            SeedCandidates();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void SeedVoters() throws Exception {
        String defaultPassword = "password";
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        KeySpec spec = new PBEKeySpec(defaultPassword.toCharArray(), salt, 65536, 128);
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        String hashedPassword = Base64.getEncoder().encodeToString(factory.generateSecret(spec).getEncoded());
        ArrayList<Voter> voters = new ArrayList<Voter>();
        voters.add(new Voter(12345L, "John", "Brown", hashedPassword, Base64.getEncoder().encodeToString(salt), userService.generateSessionToken()));
        voters.add(new Voter(54321L, "Mary", "Town", hashedPassword, Base64.getEncoder().encodeToString(salt), userService.generateSessionToken()));
        voters.removeIf(voter -> voterRepository.findByVoterId(voter.getVoterId()) != null);
        if (voters.isEmpty()) {
            return;
        }
        voterRepository.saveAll(voters);
    }

    private void SeedElections() {
        ArrayList<Election> elections = new ArrayList<>();
        elections.add(new Election("UWI Guild Presidency", "The election to determine the UWI guild president."));
        elections.add(new Election("UWI Guild Vice Presidency (VPSSP)", "The election to determine the UWI guild vice president."));
        elections.add(new Election("Secretary", "The election to determine the UWI guild secretary."));
        elections.removeIf(election -> electionRepository.findByName(election.getName()) != null);
        if (elections.isEmpty()) {
            return;
        }
        electionRepository.saveAll(elections);
    }

    private void SeedAdmins() throws Exception {
        String defaultPassword = "password";
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        KeySpec spec = new PBEKeySpec(defaultPassword.toCharArray(), salt, 65536, 128);
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        String hashedPassword = Base64.getEncoder().encodeToString(factory.generateSecret(spec).getEncoded());
        ArrayList<Admin> admins = new ArrayList<>();
        admins.add(new Admin("admin", "Demo", "Project", hashedPassword, Base64.getEncoder().encodeToString(salt)));
        admins.removeIf(admin -> adminRepository.findByUserName(admin.getUserName()) != null);
        if (admins.isEmpty()) {
            return;
        }
        adminRepository.saveAll(admins);
    }

    private void SeedCandidates() {
        Election presidency = electionRepository.findByName("UWI Guild Presidency");
        Election vicePresidency = electionRepository.findByName("UWI Guild Vice Presidency (VPSSP)");
        Election secretary = electionRepository.findByName("Secretary");
        ArrayList<Candidate> candidates = new ArrayList<>();
        Candidate bruce = new Candidate("Bruce", "Wayne");
        candidates.add(bruce);
        Candidate kent = new Candidate("Clark", "Kent");
        candidates.add(kent);
        Candidate tony = new Candidate("Tony", "Stark");
        candidates.add(tony);
        Candidate peter = new Candidate("Peter", "Parker");
        candidates.add(peter);
        bruce.setElection(presidency);
        kent.setElection(presidency);
        tony.setElection(secretary);
        peter.setElection(vicePresidency);
        //candidates.forEach(candidate -> candidate.setElection(presidency));
        candidates.removeIf(candidate -> candidateRepository.findByName(candidate.getFirstName(), candidate.getLastName()) != null);
        if (candidates.isEmpty()) {
            return;
        }
        candidateRepository.saveAll(candidates);
    }
}
