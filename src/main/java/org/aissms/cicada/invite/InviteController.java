package org.aissms.cicada.invite;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.aissms.cicada.conversation.ConversationService;
import org.aissms.cicada.user.User;
import org.aissms.cicada.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


// for now service code is in controller 
// move to service layer later
@RestController
@RequestMapping("/invite")
public class InviteController {
    @Autowired UserRepository repository;
    @Autowired ConversationService conversationService;

    @GetMapping("/sent")
    public List<User> getSentInvites(Authentication auth) {
        User user = repository.findByEmail(auth.getName());
        if(user.getInviteTo() == null) return Collections.emptyList();
        return repository.findByIdIn(new ArrayList<String>(user.getInviteTo()));
    }
    @GetMapping("/received")
    public List<User> getReceivedInvites(Authentication auth) {
        User user = repository.findByEmail(auth.getName());
        if(user.getInviteFrom() == null) return Collections.emptyList();
        return repository.findByIdIn(new ArrayList<String>(user.getInviteFrom()));
    }
    @PostMapping("/send/{email}")
    public ResponseEntity<String> sendInvite(Authentication auth, @PathVariable String email) {
        User thatUser = repository.findByEmail(email);
        if(thatUser == null) {
            return new ResponseEntity<String>("email is not on platform!", HttpStatus.BAD_REQUEST);
        }
        User myself = repository.findByEmail(auth.getName());
        myself.getInviteTo().add(thatUser.getId());
        thatUser.getInviteFrom().add(myself.getId());
        repository.save(myself);
        repository.save(thatUser);
        return new ResponseEntity<String>("success", HttpStatus.OK);
    }
    @PostMapping("/delete/{email}")
    public ResponseEntity<String> deleteInvite(Authentication auth, @PathVariable String email) {
        User thatUser = repository.findByEmail(email);
        if(thatUser == null) {
            return new ResponseEntity<String>("email not found", HttpStatus.BAD_REQUEST);
        }
        User myself = repository.findByEmail(auth.getName());
        myself.getInviteFrom().remove(thatUser.getId());
        myself.getInviteTo().remove(thatUser.getId());
        repository.save(myself);
        return new ResponseEntity<String>(HttpStatus.OK);
    }
    @PostMapping("/accept/{email}")
    public ResponseEntity<User> acceptInvite(Authentication auth, @PathVariable String email) {
        User thatUser = repository.findByEmail(email);
        if(thatUser == null) {
            return new ResponseEntity<User>(HttpStatus.BAD_REQUEST);
        }
        User myself = repository.findByEmail(auth.getName());
        if(!myself.getInviteFrom().contains(thatUser.getId())) {
            return new ResponseEntity<User>(HttpStatus.BAD_REQUEST);
        }
        conversationService.createNewConversation(thatUser, myself);
        return new ResponseEntity<User>(thatUser, HttpStatus.OK);
    }
}
