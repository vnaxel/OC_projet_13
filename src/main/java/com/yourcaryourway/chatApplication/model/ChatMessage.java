package com.yourcaryourway.chatApplication.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ChatMessage {
    private String sender;
    private String content;
    private ChatMessageType type;
}
