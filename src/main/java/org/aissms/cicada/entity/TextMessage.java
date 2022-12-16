package org.aissms.cicada.entity;

public class TextMessage {
    private String sender;
    private String recver;
    private String content;

    public TextMessage(String sender, String recver, String content) {
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
