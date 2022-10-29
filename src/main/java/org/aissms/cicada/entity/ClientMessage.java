package org.aissms.cicada.entity;

public class ClientMessage {
    private String sender;
    private String recver;
    private String content;

    public ClientMessage(String sender, String recver, String content) {
        this.sender = sender;
        this.recver = recver;
        this.content = content;
    }
    
    public String getSender() {
        return sender;
    }
    public String getRecver() {
        return recver;
    }
    public String getContent() {
        return content;
    }
}
