package org.aissms.cicada.messaging;

import org.aissms.cicada.entity.ImageMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;

@Controller
public class ImageController {
    
    @Autowired SimpMessagingTemplate template;

    @MessageMapping("/upload/image")
    public void imageController(Message<ImageMessage> message, OAuth2AuthenticationToken token) {
        ImageMessage ms = message.getPayload();
        String name = token.getPrincipal().getAttribute("login");
        if(name != null && name.equalsIgnoreCase(ms.getSender())) {
            template.convertAndSend("/messages/" + ms.getRecver(), ms);
        }
    }
}
