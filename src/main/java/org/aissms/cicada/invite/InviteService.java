package org.aissms.cicada.invite;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.aissms.cicada.conversation.ConversationService;
import org.aissms.cicada.user.User;
import org.aissms.cicada.user.UserDto;
import org.aissms.cicada.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class InviteService {
    @Autowired UserRepository repository;
    @Autowired ConversationService conversationService;

    public List<UserDto> getSentInvites(String email) {
        User user = repository.findByEmail(email);
        if(user.getInviteSent() == null) return Collections.emptyList();
        List<User> userList = repository.findByIdIn(new ArrayList<String>(user.getInviteSent()));
        return User.mapToUserDto(userList);
    }

    public List<UserDto> getReceivedInvites(String email) {
        User user = repository.findByEmail(email);
        if(user.getInviteReceived() == null) return Collections.emptyList();
        List<User> userList = repository.findByIdIn(new ArrayList<String>(user.getInviteReceived()));
        return User.mapToUserDto(userList);
    }

    public boolean sendInvite(String email1, String email2) {
        User thatUser = repository.findByEmail(email2);
        if(thatUser == null) {
            return false;
        }
        User myself = repository.findByEmail(email1);
        myself.getInviteSent().add(thatUser.getId());
        thatUser.getInviteReceived().add(myself.getId());
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
        myself.getInviteReceived().remove(thatUser.getId());
        myself.getInviteSent().remove(thatUser.getId());
        repository.save(myself);
        return true;
    }
    public UserDto acceptInvite(Authentication auth, String email) {
        User thatUser = repository.findByEmail(email);
        if(thatUser == null) {
            return null;
        }
        User myself = repository.findByEmail(auth.getName());
        if(!myself.getInviteReceived().contains(thatUser.getId())) {
            return null;
        }
        conversationService.createNewConversation(thatUser, myself);
        return thatUser.mapToUserDto();
    }
}
