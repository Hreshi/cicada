package org.aissms.cicada.auth;

import org.aissms.cicada.security.JwtTokenUtil;
import org.aissms.cicada.user.User;
import org.aissms.cicada.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegisterController {
    @Autowired UserRepository repository;
    @Autowired PasswordEncoder encoder;
    @Autowired JwtTokenUtil jwtTokenUtil;

    @PostMapping("/register")
    public String registerUser(RegisterForm form) {
        System.out.println("Begin register");
        User user = repository.findByEmail(form.getEmail());
        if(user != null) {
            return "email already exists!";
        }
        user = mapToUser(form);
        user.setPassword(encoder.encode(user.getPassword()));
        repository.save(user);
        String token = jwtTokenUtil.generateToken(getUserDetails(form.getEmail(), form.getPassword()));
        System.out.println("Generate token : " + token);
        return token;
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
