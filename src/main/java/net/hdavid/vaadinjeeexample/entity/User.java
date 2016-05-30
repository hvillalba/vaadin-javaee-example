package net.hdavid.vaadinjeeexample.entity;


import java.util.List;

public class User {

    public User(String username) {
        this.username= username;
    }
    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isInAnyRole(List<String> roles) {
        return roles.contains(username);
    }
}
