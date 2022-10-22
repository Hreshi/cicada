package org.aissms.cicada.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthController {
    
    @GetMapping("/auth")
    public String getAuthPage(@AuthenticationPrincipal OAuth2User principal) {
        if(principal != null && principal.getAttribute("login") != null) {
            return "redirect:home";
        }
        return "auth";
    }
}
