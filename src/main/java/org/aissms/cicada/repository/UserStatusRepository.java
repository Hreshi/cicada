package org.aissms.cicada.repository;

import java.util.Hashtable;

import org.aissms.cicada.entity.UserStatus;
import org.springframework.stereotype.Component;

@Component
public class UserStatusRepository {
    private static Hashtable<String, String> table = new Hashtable<String, String>();

    public UserStatus getStatus(String username) {
        String status = table.get(username);
        return new UserStatus(username, status == null ? "offline" : status);
    }
    
    public void setOnline(String username) {
        table.put(username, "online");
    }
    
    public void setTyping(String username) {
        table.put(username, "typing");
    }

    public void setOffline(String username) {
        table.remove(username);
    }
}
