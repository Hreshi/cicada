package org.aissms.cicada.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LogoutController {
    
    @RequestMapping("/logout")
    public String logout(@AuthenticationPrincipal OAuth2User principal) {
        if(principal != null && principal.getAttribute("login") != null) {
            
        }
        return "auth";
    }
}
