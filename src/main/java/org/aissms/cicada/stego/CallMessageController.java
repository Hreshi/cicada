package org.aissms.cicada.stego;

import java.util.Date;

import org.aissms.cicada.messaging.MyMessageDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

@Controller
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CallMessageController {
    
    @Autowired SimpMessagingTemplate messagingTemplate;
    @Autowired CallRepository callRepository;

    @MessageMapping("/secure")
    public void sendMessage(@Payload String message, Authentication auth) {
        Call call = callRepository.getCall(auth.getName());
        if(call == null) return;

        String secondUser = call.getEmail1();
        if(secondUser.equals(auth.getName())) {
            secondUser = call.getEmail2();
        }
        MyMessageDto dto = new MyMessageDto();
        dto.setAuthor(auth.getName());
        dto.setBlockIndex(0);
        dto.setContent(message);
        dto.setMessagetype("SECRET");
        dto.setDate(new Date());
        messagingTemplate.convertAndSend("/messages/"+secondUser, dto);
    }
}
