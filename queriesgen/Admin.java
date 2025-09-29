package com.example.queriesgen;

public class Admin {
    private String email;
    private String role;

    public Admin() {}

    public Admin(String email, String role) {
        this.email = email;
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public String getRole() {
        return role;
    }
}

