package org.aissms.cicada.messaging;

import org.aissms.cicada.entity.UserStatusMessage;
import org.aissms.cicada.service.UserStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class UserStatusController {
    
    @Autowired UserStatusService service;
    @Autowired SimpMessagingTemplate template;

    @MessageMapping("/user/status")
    public void userStatusController(UserStatusMessage message) {
        template.convertAndSend("/messages/"+message.getRecver(), service.getStatus(message.getUser()));
    } 
}
