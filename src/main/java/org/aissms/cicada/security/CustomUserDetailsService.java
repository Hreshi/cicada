package org.aissms.cicada.security;

import org.aissms.cicada.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class CustomUserDetailsService implements UserDetailsService{
    @Autowired UserRepository repository;
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        org.aissms.cicada.user.User user = repository.findByEmail(username);
        if(user == null) {
            throw new UsernameNotFoundException("email not registered");
        }
        return User.withUsername(username).password(user.getPassword()).build();
    }
    
}
