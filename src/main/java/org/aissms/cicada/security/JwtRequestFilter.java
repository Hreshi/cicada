package org.aissms.cicada.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

// filter to extract jwt and set securitycontextholder
@Component
public class JwtRequestFilter extends OncePerRequestFilter {
    @Autowired JwtTokenUtil jwtTokenUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = request.getParameter("token");
        if(token == null) {
            token = extractJwtToken(request);
        }
        if(token != null) {
            try {
                String email = jwtTokenUtil.getUsernameFromToken(token);
                System.out.println(email+"\n\n");
                if(email != null || !jwtTokenUtil.isTokenExpired(token)) {
                    setAuthenticated(email);
                }
            } catch (Exception e) {
                System.out.println("token not found");
            }
        }
        // go to next filters
        filterChain.doFilter(request, response);
    }
    private String extractJwtToken(HttpServletRequest request) {
        String value = request.getHeader("Authorization");
        if(value != null && value.startsWith("Bearer ")) {
            return value.substring(7);
        }
        return null;
    }
    private void setAuthenticated(String email) {
        // authorities must be passed, can be null
        Authentication auth = new UsernamePasswordAuthenticationToken(email, email, null);
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(auth);
        SecurityContextHolder.setContext(context);
    }
}
