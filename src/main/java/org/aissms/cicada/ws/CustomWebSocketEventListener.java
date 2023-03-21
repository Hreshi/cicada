package org.aissms.cicada.ws;

import org.aissms.cicada.status.StatusService;
import org.aissms.cicada.stego.StegoChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
public class CustomWebSocketEventListener{

    @Autowired StatusService statusService;
    @Autowired StegoChatService stegoChatService;

    @EventListener
    public void connectedEvent(SessionConnectedEvent event) {
        String email = event.getUser().getName();
        statusService.setStatus(email);
    }

    @EventListener
    public void disconnectedEvent(SessionDisconnectEvent event) {
        String email = event.getUser().getName();
        statusService.removeStatus(email);
        stegoChatService.endCall(email);
    }
}
