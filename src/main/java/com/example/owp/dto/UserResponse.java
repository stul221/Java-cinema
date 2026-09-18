package com.example.owp.dto;


import com.example.owp.model.User;

public class UserResponse {
    private String userName;
    private String email;
    private String role;
    private int count;

    public UserResponse(User user) {
        this.userName = user.getUserName();
        this.email = user.getEmail();
        this.role = user.getRole().name();
        this.count = user.getCount();
    }

    public String getUserName() {
        return this.userName;
    }

    public String getEmail() {
        return this.email;
    }

    public String getRole() { return  this.role; }

    public int getCount() { return  this.count; }
}
