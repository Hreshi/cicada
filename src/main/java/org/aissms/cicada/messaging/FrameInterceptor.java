package org.aissms.cicada.messaging;

import org.aissms.cicada.entity.ClientMessage;
import org.springframework.lang.Nullable;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Component;

/* 
    Deny user to subscribe to "/messages/*"
    User can only subscribe to "/message/username"
*/

@Component
public class FrameInterceptor implements ChannelInterceptor {

    @Override
    @Nullable
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        StompCommand command = accessor.getCommand();
        OAuth2AuthenticationToken token = (OAuth2AuthenticationToken) accessor.getUser();
        String sender = token.getPrincipal().getAttribute("login");

        if(StompCommand.SUBSCRIBE.equals(command)) {
            String template = "/messages/" + sender;
            String dest = accessor.getDestination();
            if(template.equalsIgnoreCase(dest)) {
                return message;
            }
        }

        return null; // discard message
    }
    
}
