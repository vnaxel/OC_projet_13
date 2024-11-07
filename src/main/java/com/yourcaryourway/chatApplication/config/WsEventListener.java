package com.yourcaryourway.chatApplication.config;

import com.yourcaryourway.chatApplication.model.ChatMessage;
import com.yourcaryourway.chatApplication.controller.ChatController;
import com.yourcaryourway.chatApplication.model.ChatMessageType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.context.event.EventListener;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
@RequiredArgsConstructor
@Slf4j
public class WsEventListener {

    private final ChatController ChatController;

    @EventListener
    public void handleWsDisconnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        String username = (String) headerAccessor.getSessionAttributes().get("username");
        if (username != null) {
            log.info("User Disconnected: {}", username);
            var message = ChatMessage.builder()
                    .type(ChatMessageType.LEAVE)
                    .sender(username)
                    .build();
            ChatController.send(message);
        }
    }
}
