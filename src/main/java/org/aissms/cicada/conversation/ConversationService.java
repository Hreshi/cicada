package org.aissms.cicada.conversation;

import java.util.ArrayList;
import java.util.List;

import org.aissms.cicada.block.MessageBlock;
import org.aissms.cicada.block.MessageBlockRepository;
import org.aissms.cicada.user.User;
import org.aissms.cicada.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class ConversationService {
    
    @Autowired ConversationRepository conversationRepository;
    @Autowired UserRepository userRepository;
    @Autowired MessageBlockRepository blockRepository;
    
    public List<User> getAllConversations(Authentication auth) {
        User user = userRepository.findByEmail(auth.getName());        
        return userRepository.findByIdIn(new ArrayList<String>(user.getConversation().keySet()));
    }
    public void createNewConversation(User user1, User user2) {
        Conversation conv = new Conversation();
        conv.setUser1(user1.getId());
        conv.setUser2(user2.getId());
        conv.setMessageBlockId(new ArrayList<>());
        MessageBlock block = MessageBlock.genesisBlock();
        blockRepository.save(block);
        conv.addBlock(block);
        conversationRepository.save(conv);
        user1.getConversation().put(user2.getId(), conv.getId());
        user2.getConversation().put(user1.getId(), conv.getId());

        // remove id from invitefrom and inviteto
        user1.getInviteFrom().remove(user2.getId());
        user1.getInviteTo().remove(user2.getId());
        user2.getInviteFrom().remove(user1.getId());
        user2.getInviteTo().remove(user1.getId());

        userRepository.save(user1);
        userRepository.save(user2);
    }
    public Conversation getConversationWith(Authentication auth, String email) {
        User myself = userRepository.findByEmail(auth.getName());
        User friend = userRepository.findByEmail(email);
        System.out.println("Before");
        if(friend == null) return null;
        System.out.println("friend not null");

        String convId = myself.getConversation().get(friend.getId());
        if(convId == null) return null;
        System.out.println("convid not null");
        return conversationRepository.findById(convId).get();
    }
}
