package com.example.evote.services;

import com.example.evote.interfaces.AdminRepository;
import com.example.evote.interfaces.VoterRepository;
import com.example.evote.models.Admin;
import com.example.evote.models.Voter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.SecureRandom;
import java.security.spec.KeySpec;
import java.util.Base64;

@Component
public class UserService {
    @Autowired
    private VoterRepository voterRepository;

    @Autowired
    private AdminRepository adminRepository;

    public Admin getAdminUser(String userName) {
        return adminRepository.findByUserName(userName);
    }

    public Voter getVoter(Long voterId) {
        return voterRepository.findByVoterId(voterId);
    }

    public String getHashedPassword(String password, String salt) {
        try {
            KeySpec spec = new PBEKeySpec(password.toCharArray(), Base64.getDecoder().decode(salt), 65536, 128);
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            String hashedPassword = Base64.getEncoder().encodeToString(factory.generateSecret(spec).getEncoded());
            return hashedPassword;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return "";
    }

    public String generateSessionToken() {
        try {
            SecureRandom random = new SecureRandom();
            byte[] sessionToken = new byte[16];
            random.nextBytes(sessionToken);
            return Base64.getEncoder().encodeToString(sessionToken).replace('+', '0').replace('/', '1').replace('=', '3');
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return "";
    }
}
