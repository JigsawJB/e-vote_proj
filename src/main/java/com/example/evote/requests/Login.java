package com.example.evote.requests;

public class Login {
    private final String userName;
    private final String password;
    private final Boolean isAdmin;

    public Login(String userName, String password, Boolean isAdmin) {
        this.userName = userName;
        this.password = password;
        this.isAdmin = isAdmin;
    }

    public String getUserName() {
        return this.userName;
    }

    public String getPassword() {
        return this.password;
    }

    public Boolean getIsAdmin() {
        return this.isAdmin;
    }
}
