package org.aissms.cicada.messaging;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MyMessage {
    private int blockIndex;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private int index;
    private String content;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Date time;
    
    public int getIndex() {
        return index;
    }
    public void setIndex(int index) {
        this.index = index;
    }
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
    public int getBlockIndex() {
        return blockIndex;
    }
    public void setBlockIndex(int blockIndex) {
        this.blockIndex = blockIndex;
    }
}
