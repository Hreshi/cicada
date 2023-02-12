package org.aissms.cicada.conversation;

import java.util.List;

import org.aissms.cicada.block.MessageBlock;
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
    public int size() {
        return messageBlockId.size();
    }
    public String getLastBlockId() {
        int index = messageBlockId.size()-1;
        return messageBlockId.get(index);
    }
    public void addBlock(MessageBlock block) {
        messageBlockId.add(block.getId());
    }
}
