package org.aissms.cicada.stego;

import java.util.Date;
import java.util.List;

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

        String email1 = call.getEmail1();
        String email2 = call.getEmail2();

        if(call.canStore()) {
            List<String> list1 = call.getMsgFromEmail1();
            List<String> list2 = call.getMsgFromEmail2();

            if(email1.equals(auth.getName())) {
                list1.add(message);
            } else {
                list2.add(message);
            }
            if(list1.size() > 0 && list2.size() > 0) {
                call.stopStoring();
                for(String msg : list1) {
                    messagingTemplate.convertAndSend("/messages/"+email2, toDto(email1, msg));
                }
                for(String msg : list2) {
                    messagingTemplate.convertAndSend("/messages/"+email1, toDto(email1, msg));
                }
                list1.clear();
                list2.clear();
            }
        } else {
            System.out.println("\n\nMessage : " + message + "\n\n");
            MyMessageDto dto = toDto(auth.getName(), message);
            if(email1.equals(auth.getName())) {
                messagingTemplate.convertAndSend("/messages/"+email2, dto);
            } else {
                messagingTemplate.convertAndSend("/messages/"+email1, dto);
            }
        }
    }
    private MyMessageDto toDto(String email, String msg) {
        MyMessageDto dto = new MyMessageDto();
        dto.setAuthor(email);
        dto.setContent(msg);
        dto.setDate(new Date());
        dto.setMessagetype("SECRET");
        return dto;
    }
}
