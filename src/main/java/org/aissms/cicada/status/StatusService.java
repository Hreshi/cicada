package org.aissms.cicada.status;

import org.aissms.cicada.user.User;
import org.aissms.cicada.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class StatusService {
    @Autowired StatusRepository statusRepository;
    @Autowired UserRepository userRepository;

    public Status getStatus(Authentication auth, String email) {
        User user1 = userRepository.findByEmail(auth.getName());
        User user2 = userRepository.findByEmail(email);
        if(user1 == null || user2 == null || !friends(user1, user2)) return null;

        return statusRepository.online(email) ? Status.ONLINE : Status.OFFLINE;
    }
    public void setStatus(String email) {
        statusRepository.put(email);
    }
    private boolean friends(User user1, User user2) {
        return user1.getConversation().containsKey(user2.getId());
    }
}
