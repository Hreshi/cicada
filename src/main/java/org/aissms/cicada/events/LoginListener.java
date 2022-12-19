package org.aissms.cicada.events;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.aissms.cicada.entity.User;
import org.aissms.cicada.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

// now i understood the use of inheritance much better
@Component
public class LoginListener extends SavedRequestAwareAuthenticationSuccessHandler {
    @Autowired UserRepository userRepository;
    
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,Authentication authentication) throws ServletException, IOException {
        OAuth2User oauth = (OAuth2User) authentication.getPrincipal();
        User user = userRepository.findByUsername(oauth.getAttribute("login"));
        if(user == null) {
            user = new User(oauth.getAttribute("login"), oauth.getAttribute("avatar_url"));
            userRepository.save(user);
        }
        super.onAuthenticationSuccess(request, response, authentication);
    }

    
}