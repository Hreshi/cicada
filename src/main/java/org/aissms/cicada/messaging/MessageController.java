package org.aissms.cicada.messaging;

import org.aissms.cicada.block.MessageBlock;
import org.aissms.cicada.block.MessageBlockRepository;
import org.aissms.cicada.conversation.Conversation;
import org.aissms.cicada.conversation.ConversationRepository;
import org.aissms.cicada.conversation.ConversationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MessageController {
    @Autowired ConversationService conversationService;
    @Autowired ConversationRepository conversationRepository;
    @Autowired MessageBlockRepository blockRepository;

    @MessageMapping("/send/{email}")
    public void sendMessage(@Payload String message, @DestinationVariable("email") String email, Authentication auth) {
        
        Conversation conv = conversationService.getConversationWith(auth.getName(), email);

        // drop message as there is no conversation
        if(conv == null) return;

        MessageBlock block = blockRepository.findById(conv.getLastBlockId()).get();
        if(block == null) return;

        block.addMessage(message);
        blockRepository.save(block);
        if(block.isFull()) {
            MessageBlock block2  = MessageBlock.nextBlock(block);
            blockRepository.save(block2);
            conv.addBlock(block2);
            conversationRepository.save(conv);
        }

        // add ws notification code here
    }
    @GetMapping("/home")
    public String homePage() {
        return "home";
    }
}
