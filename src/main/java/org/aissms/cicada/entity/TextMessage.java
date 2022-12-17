package org.aissms.cicada.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TextMessage")
public class TextMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String sender;
    private String recver;
    private String content;
    
    public TextMessage() {
    }
    public TextMessage(Long id, String sender, String recver, String content) {
        this.id = id;
        this.sender = sender;
        this.recver = recver;
        this.content = content;
    }
    public Long getId() {
        return id;
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
    public void setId(Long id) {
        this.id = id;
    }
    public void setSender(String sender) {
        this.sender = sender;
    }
    public void setRecver(String recver) {
        this.recver = recver;
    }
    public void setContent(String content) {
        this.content = content;
    }
}
