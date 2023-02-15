package org.aissms.cicada.messaging;

import java.util.Date;

public class MyMessage {
    private String authorId;
    private String content;
    private Date time;
    
    
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public Date getTime() {
        return time;
    }
    public void setTime(Date time) {
        this.time = time;
    }
    public void setTime() {
        this.time = new Date();
    }
    public String getAuthorId() {
        return authorId;
    }
    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

    // dto mapping
    public MyMessageDto map(int blockIndex, int messageIndex, String author) {
        MyMessageDto dto = new MyMessageDto();
        dto.setBlockIndex(blockIndex);
        dto.setMessageIndex(messageIndex);
        dto.setAuthor(author);
        dto.setContent(author);
        dto.setDate(time);
        return dto;
    }
}
