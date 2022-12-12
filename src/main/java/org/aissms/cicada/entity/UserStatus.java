package org.aissms.cicada.entity;

public class UserStatus {
    private String username;
    private String status;
    
    public UserStatus(String username, String status) {
        this.username = username;
        this.status = status;
    }

    public String getUsername() {
        return username;
    }

    public String getStatus() {
        return status;
    }
}
