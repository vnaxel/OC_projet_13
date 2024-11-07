package com.yourcaryourway.chatApplication.controller;

import com.yourcaryourway.chatApplication.model.ChatMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

import java.util.Objects;

@Controller
public class ChatController {

    @MessageMapping("chat.send")
    @SendTo("/topic/public")
    public ChatMessage send(@Payload ChatMessage message) {
        return message;
    }

    @MessageMapping("chat.join")
    @SendTo("/topic/chat")
    public ChatMessage join(@Payload ChatMessage message, SimpMessageHeaderAccessor headerAccessor) {
        Objects.requireNonNull(headerAccessor.getSessionAttributes()).put("username", message.getSender());
        return message;
    }
}
