package org.aissms.cicada.conversation;

import java.util.List;

import org.springframework.data.annotation.Id;

public class Conversation {
    @Id
    String id;
    String user1;
    String user2;
    List<String> messageBlockId;

    public List<String> getMessageBlockId() {
        return messageBlockId;
    }
    public void setMessageBlockId(List<String> messageBlockId) {
        this.messageBlockId = messageBlockId;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getUser1() {
        return user1;
    }
    public void setUser1(String user1) {
        this.user1 = user1;
    }
    public String getUser2() {
        return user2;
    }
    public void setUser2(String user2) {
        this.user2 = user2;
    }
}
