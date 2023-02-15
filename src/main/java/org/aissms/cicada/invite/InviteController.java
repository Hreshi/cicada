package org.aissms.cicada.invite;

import java.util.List;

import org.aissms.cicada.user.UserDto;
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
    @Autowired InviteService service;

    @GetMapping("/sent")
    public List<UserDto> getSentInvites(Authentication auth) {
        return service.getSentInvites(auth.getName());
    }
    @GetMapping("/received")
    public List<UserDto> getReceivedInvites(Authentication auth) {
        return service.getReceivedInvites(auth.getName());
    }
    @PostMapping("/send/{email}")
    public ResponseEntity<String> sendInvite(Authentication auth, @PathVariable String email) {
        if(!service.sendInvite(auth.getName(), email)) {
            return new ResponseEntity<String>("email is not on platform!", HttpStatus.BAD_REQUEST);
        }
        
        return new ResponseEntity<String>("success", HttpStatus.OK);
    }
    @PostMapping("/delete/{email}")
    public ResponseEntity<String> deleteInvite(Authentication auth, @PathVariable String email) {
        if(!service.deleteInvite(auth, email)) {
            return new ResponseEntity<String>("failed to delete", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<String>("success", HttpStatus.OK);
    }
    @PostMapping("/accept/{email}")
    public ResponseEntity<UserDto> acceptInvite(Authentication auth, @PathVariable String email) {
        UserDto user = service.acceptInvite(auth, email);
        if(user == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<UserDto>(user, HttpStatus.OK);
    }
}
