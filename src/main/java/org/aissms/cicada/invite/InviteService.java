package org.aissms.cicada.invite;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.aissms.cicada.conversation.ConversationService;
import org.aissms.cicada.user.User;
import org.aissms.cicada.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;

public class InviteService {
    @Autowired UserRepository repository;
    @Autowired ConversationService conversationService;

    public List<User> getSentInvites(String email) {
        User user = repository.findByEmail(email);
        if(user.getInviteTo() == null) return Collections.emptyList();
        return repository.findByIdIn(new ArrayList<String>(user.getInviteTo()));
    }

    public List<User> getReceivedInvites(String email) {
        User user = repository.findByEmail(email);
        if(user.getInviteFrom() == null) return Collections.emptyList();
        return repository.findByIdIn(new ArrayList<String>(user.getInviteFrom()));
    }

    public boolean sendInvite(String email1, String email2) {
        User thatUser = repository.findByEmail(email2);
        if(thatUser == null) {
            return false;
        }
        User myself = repository.findByEmail(email1);
        myself.getInviteTo().add(thatUser.getId());
        thatUser.getInviteFrom().add(myself.getId());
        repository.save(myself);
        repository.save(thatUser);
        return true;
    }
    public boolean deleteInvite(Authentication auth, String email) {
        User thatUser = repository.findByEmail(email);
        if(thatUser == null) {
            return false;
        }
        User myself = repository.findByEmail(auth.getName());
        myself.getInviteFrom().remove(thatUser.getId());
        myself.getInviteTo().remove(thatUser.getId());
        repository.save(myself);
        return true;
    }
    public User acceptInvite(Authentication auth, String email) {
        User thatUser = repository.findByEmail(email);
        if(thatUser == null) {
            return null;
        }
        User myself = repository.findByEmail(auth.getName());
        if(!myself.getInviteFrom().contains(thatUser.getId())) {
            return null;
        }
        conversationService.createNewConversation(thatUser, myself);
        return thatUser;
    }
}
