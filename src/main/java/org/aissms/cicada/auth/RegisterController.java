package org.aissms.cicada.auth;

import java.util.HashMap;
import java.util.HashSet;

import org.aissms.cicada.security.JwtTokenUtil;
import org.aissms.cicada.user.User;
import org.aissms.cicada.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;


// registers user and fills empty map for conversation, set for invites
@RestController
public class RegisterController {
    @Autowired UserService userService;
    @Autowired PasswordEncoder encoder;
    @Autowired JwtTokenUtil jwtTokenUtil;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(RegisterForm form) {
        User user = userService.findByEmail(form.getEmail());
        if(user != null) {
            return new ResponseEntity<String>("email already exists", HttpStatus.FORBIDDEN);
        }
        user = mapToUser(form);
        user.setPassword(encoder.encode(user.getPassword()));
        
        // fill empty map and sets in db 
        user.setConversation(new HashMap<String, String>());
        user.setInviteReceived(new HashSet<String>());
        user.setInviteSent(new HashSet<String>());

        userService.save(user);
        String token = jwtTokenUtil.generateToken(getUserDetails(form.getEmail(), form.getPassword()));

        return new ResponseEntity<String>(token, HttpStatus.OK);
    }
    
    private User mapToUser(RegisterForm form) {
        User user = new User();
        user.setEmail(form.getEmail());
        user.setName(form.getName());
        user.setAvatarUrl(form.getAvatarUrl());
        user.setPassword(form.getPassword());
        return user;
    }
    private UserDetails getUserDetails(String username, String password) {
        return org.springframework.security.core.userdetails.User
            .builder()
            .username(username)
            .password(password)
            .authorities("ROLE_USER")
            .build();
    }
}
