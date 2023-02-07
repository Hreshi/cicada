package org.aissms.cicada.auth;

import org.aissms.cicada.security.JwtTokenUtil;
import org.aissms.cicada.user.User;
import org.aissms.cicada.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.crypto.password.PasswordEncoder;


@RestController
public class LoginController {
    @Autowired UserRepository repository;
    @Autowired PasswordEncoder encoder;
    @Autowired JwtTokenUtil jwtTokenUtil;

    @PostMapping("/login")
    public ResponseEntity<String> authenticateUser(LoginForm form) {
        User user = repository.findByEmail(form.getEmail());
        form.setPassword(encoder.encode(form.getPassword()));
        if(user == null || !user.getPassword().equals(form.getPassword())) {
            return new ResponseEntity<String>("invalid cred", HttpStatus.FORBIDDEN);
        }
        String token = jwtTokenUtil.generateToken(mapToUserDetails(user));
        return new ResponseEntity<String>(token, HttpStatus.OK);
    }
    private UserDetails mapToUserDetails(User user) {
        return org.springframework.security.core.userdetails.User
            .builder()
            .username(user.getEmail())
            .password(user.getPassword())
            .authorities("ROLE_USER")
            .build();
    }
}
