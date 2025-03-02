package com.pulse.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {

    // Maps messages from /app/chat
    @MessageMapping("/chat")
    // Sends the received message to all subscribers of /topic/messages
    @SendTo("/topic/messages")
    public String send(String message) throws Exception {
        // You can add processing here if needed (e.g., filtering, logging, etc.)
        return message;
    }
}
