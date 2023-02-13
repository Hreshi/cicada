package org.aissms.cicada.block;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.aissms.cicada.messaging.MyMessage;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class MessageBlock {
    @JsonIgnore
    private static int BLOCK_SIZE = 25;
    @JsonIgnore
    private String id;
    private int index;
    private List<MyMessage> messageList;

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public int getIndex() {
        return index;
    }
    public void setIndex(int index) {
        this.index = index;
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
    public static MessageBlock genesisBlock() {
        MessageBlock block = new MessageBlock();
        block.index = 1;
        block.messageList = new ArrayList<MyMessage>();
        return block;
    }
    public void addMessage(String message) {
        MyMessage msg = new MyMessage();
        msg.setIndex(messageList.size()+1);
        msg.setBlockIndex(index);
        msg.setTime(new Date());
        msg.setContent(message);
        messageList.add(msg);
    }
    // create next block with index+1
    public static MessageBlock nextBlock(MessageBlock block) {
        MessageBlock block2 = new MessageBlock();
        block2.setIndex(block.getIndex()+1);
        block2.setMessageList(new ArrayList<MyMessage>());
        return block2;
    }
}
