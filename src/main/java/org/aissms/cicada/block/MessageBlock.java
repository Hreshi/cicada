package org.aissms.cicada.block;

import java.util.ArrayList;
import java.util.List;

import org.aissms.cicada.messaging.MyMessage;

public class MessageBlock {
    private static int BLOCK_SIZE = 25;
    private String id;
    private List<MyMessage> messageList;

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public List<MyMessage> getMessageList() {
        return messageList;
    }
    public void setMessageList(List<MyMessage> messageList) {
        this.messageList = messageList;
    }

    public boolean isFull() {
        return messageList.size() >= BLOCK_SIZE;
    }
    // first block in conversation
    public static MessageBlock block() {
        MessageBlock block = new MessageBlock();
        block.messageList = new ArrayList<MyMessage>();
        return block;
    }
    public void addMessage(MyMessage message) {
        messageList.add(message);
    }

}
