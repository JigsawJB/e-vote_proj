package com.example.evote.models;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Admin {
    @Id
    private String userName;
    private String firstName;
    private String lastName;
    private String hashPassword;
    private String passwordSalt;

    protected Admin() {
    }

    public Admin(String userName, String firstName, String lastName, String hashPassword, String passwordSalt) {
        this.userName = userName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.hashPassword = hashPassword;
        this.passwordSalt = passwordSalt;
    }

    public String getUserName() {
        return this.userName;
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

    public String getPasswordSalt() {
        return this.passwordSalt;
    }
}
