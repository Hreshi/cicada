package org.aissms.cicada.block;

import org.aissms.cicada.conversation.Conversation;
import org.aissms.cicada.conversation.ConversationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageBlockService {
    @Autowired ConversationService service;
    @Autowired MessageBlockRepository repository;

    public int getBlockCount(String email1, String email2) {
        Conversation conv = service.getConversationWith(email1, email2);
        return conv == null ? -1 : conv.size();
    }

    // blocks are 1 indexed
    public MessageBlock getMessageBlock(String email1, String email2, int index) {
        if(index <= 0) return null;
        
        Conversation conv = service.getConversationWith(email1, email2);
        if(conv == null) return null;

        if(index > conv.size()) return null;

        return repository.findById(conv.getBlockId(index-1)).get();
    }
}
