package org.aissms.cicada.conversation;

import java.util.List;

import org.aissms.cicada.block.MessageBlock;
import org.springframework.data.annotation.Id;

public class Conversation {
    @Id
    private String id;
    private List<String> messageBlockId;

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
    public String getBlockId(int index) {
        return messageBlockId.get(index);
    }
}
