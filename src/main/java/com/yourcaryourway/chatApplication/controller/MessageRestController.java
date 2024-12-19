package com.yourcaryourway.chatApplication.controller;

import com.yourcaryourway.chatApplication.model.ChatMessage;
import com.yourcaryourway.chatApplication.repository.ChatMessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/chat")
public class MessageRestController {

    private final ChatMessageRepository chatMessageRepository;

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/messages")
    public List<ChatMessage> getAllMessages() {
        return chatMessageRepository.findAll();
    }
}
