package org.aissms.cicada.service;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.aissms.cicada.entity.UserStatus;
import org.springframework.stereotype.Component;

@Component
public class UserStatusService {
    private static Map<String, String> map = Collections.synchronizedMap(new HashMap<String, String>());

    public UserStatus getStatus(String name) {
        if(map.containsKey(name)) return new UserStatus(map.get(name));
        return new UserStatus("offline");
    }

    public void setStatus(String name, UserStatus status) {
        map.put(name, status.getStatus());
    }
    
    public void setStatus(String name, String status) {
        map.put(name, status);
    }

    public void remove(String name) {
        map.remove(name);
    }
}
