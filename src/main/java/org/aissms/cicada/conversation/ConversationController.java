package org.aissms.cicada.conversation;

import java.util.List;

import org.aissms.cicada.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/conversation")
public class ConversationController {
    
    @Autowired ConversationService service;

    @GetMapping("/my-conversation")
    public List<User> getAllConversation(Authentication auth) {
        return service.getAllConversations(auth);
    }
    
}
