package org.aissms.cicada.messaging;

import java.util.Date;

public class MyMessageDto {
    static String messageType = "MESSAGE";
    private int blockIndex;
    private int messageIndex;
    private String author;
    private String content;
    private Date date;
    
    public int getBlockIndex() {
        return blockIndex;
    }
    public void setBlockIndex(int blockIndex) {
        this.blockIndex = blockIndex;
    }
    public int getMessageIndex() {
        return messageIndex;
    }
    public void setMessageIndex(int messageIndex) {
        this.messageIndex = messageIndex;
    }
    public String getAuthor() {
        return author;
    }
    public void setAuthor(String author) {
        this.author = author;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }
    public String getMessageType() {
        return messageType;
    }
}
