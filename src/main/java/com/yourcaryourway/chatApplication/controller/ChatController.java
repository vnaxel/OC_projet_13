package com.yourcaryourway.chatApplication.controller;

import com.yourcaryourway.chatApplication.model.ChatMessage;
import com.yourcaryourway.chatApplication.repository.ChatMessageRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

import java.util.Objects;
@RequiredArgsConstructor
@Controller
public class ChatController {

    private final ChatMessageRepository chatMessageRepository;

    @MessageMapping("chat.send")
    @SendTo("/topic/public")
    public ChatMessage send(@Payload ChatMessage message) {
        message.setTimestamp(java.time.LocalDateTime.now());
        chatMessageRepository.save(message);
        return message;
    }

    @MessageMapping("chat.join")
    @SendTo("/topic/public")
    public ChatMessage join(@Payload ChatMessage message, SimpMessageHeaderAccessor headerAccessor) {
        message.setTimestamp(java.time.LocalDateTime.now());
        headerAccessor.getSessionAttributes().put("username", message.getSender());
        chatMessageRepository.save(message);
        return message;
    }
}
