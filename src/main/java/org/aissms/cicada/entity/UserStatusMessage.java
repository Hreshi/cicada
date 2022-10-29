package org.aissms.cicada.entity;

public class UserStatusMessage {
    private String type;
    private String user;
    private String recver;
    
    public String getRecver() {
        return recver;
    }
    public void setRecver(String recver) {
        this.recver = recver;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    
    public String getUser() {
        return user;
    }
    public void setUser(String user) {
        this.user = user;
    }
}
