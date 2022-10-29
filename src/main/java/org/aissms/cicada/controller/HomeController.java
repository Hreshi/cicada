package org.aissms.cicada.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeController {
    
    @GetMapping("/")
    public String getHomePage(@AuthenticationPrincipal OAuth2User principal) {
        if(principal != null && principal.getAttribute("login") != null) {
            return "home";
        }
        return "redirect:auth";
    }

    @GetMapping("/user")
    @ResponseBody
    public String getUserName(@AuthenticationPrincipal OAuth2User principal) {
        return principal.getAttribute("login");
    }
}
