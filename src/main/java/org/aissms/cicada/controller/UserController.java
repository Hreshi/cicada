package org.aissms.cicada.controller;

import org.aissms.cicada.entity.User;
import org.aissms.cicada.entity.UserStatus;
import org.aissms.cicada.repository.UserStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    
    @Autowired UserStatusRepository repository;

    @GetMapping("/user/data")
    public User userDataController(@AuthenticationPrincipal OAuth2User oauth) {
        return new User(oauth.getAttribute("login"), oauth.getAttribute("avatar_url"));
    }
    
    @GetMapping("/user/status/{username}")
    public UserStatus getUserStatus(@PathVariable("username") String username) {
        return repository.getStatus(username);
    }
}
