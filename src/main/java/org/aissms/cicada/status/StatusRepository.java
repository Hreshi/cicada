package org.aissms.cicada.status;

import java.util.HashSet;
import org.springframework.stereotype.Component;

@Component
public class StatusRepository {
    private static HashSet<String> statusMap = new HashSet<String>();

    public void put(String email) {
        statusMap.add(email);
    }
    public void remove(String email) {
        statusMap.remove(email);
    }
    public boolean online(String email) {
        return statusMap.contains(email);
    }
}
