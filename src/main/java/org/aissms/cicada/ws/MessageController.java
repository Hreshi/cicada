package org.aissms.cicada.ws;

import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;

// test controller to check working
@Controller
public class MessageController {
    
    @MessageMapping("/data")
    public void printData(Message<String> message, Authentication auth) {
        System.out.println(message.toString());
        System.out.println(auth.getName());
    }
}
