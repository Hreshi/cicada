package org.aissms.cicada.auth;

import org.aissms.cicada.security.JwtTokenUtil;
import org.aissms.cicada.user.User;
import org.aissms.cicada.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.crypto.password.PasswordEncoder;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class LoginController {
    @Autowired UserService userService;
    @Autowired PasswordEncoder encoder;
    @Autowired JwtTokenUtil jwtTokenUtil;

    @PostMapping("/login")
    public ResponseEntity<String> authenticateUser(@RequestBody LoginForm form) {
        User user = userService.findByEmail(form.getEmail());
        if(user == null) System.out.println("user is null");
        if(user == null || !encoder.matches(form.getPassword(), user.getPassword())) {
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
