package org.aissms.cicada.block;

import org.aissms.cicada.conversation.Conversation;
import org.aissms.cicada.conversation.ConversationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/message/{email}")
public class MessageBlockController {
    @Autowired
    ConversationService service;
    @Autowired
    MessageBlockRepository repository;

    private String email;
    
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @GetMapping("/block-count")
    public ResponseEntity<Integer> getBlockCount(Authentication auth) {
        Conversation conv = service.getConversationWith(auth, email);
        if(conv == null) {
            return new ResponseEntity<Integer>(-1, HttpStatus.BAD_REQUEST);
        }
        int size = conv.size();
        return new ResponseEntity<Integer>(size, HttpStatus.OK);
    }

    @GetMapping("/block/{index}")
    public ResponseEntity<MessageBlock> getMessageBlock(Authentication auth, @PathVariable Integer index) {
        Conversation conv = service.getConversationWith(auth, email);
        if(conv == null || index > conv.size()) {
            return new ResponseEntity<MessageBlock>(HttpStatus.BAD_REQUEST);
        }
        String blockId = conv.getMessageBlockId().get(index-1);
        MessageBlock block = repository.findById(blockId).get();
        if(block == null) {
            return new ResponseEntity<MessageBlock>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<MessageBlock>(block, HttpStatus.OK);
    }

}
