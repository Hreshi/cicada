package org.aissms.cicada.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LogoutController {
    
    @PostMapping("/logout")
    public String logoutController(Authentication auth) {
        auth.setAuthenticated(false);
        return "redirect:auth";
    }
}
