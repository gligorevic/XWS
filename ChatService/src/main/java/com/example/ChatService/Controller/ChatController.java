package com.example.ChatService.Controller;

import com.example.ChatService.Dto.MessageDTO;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {

    @MessageMapping("/hello")
    @SendTo("/message/1")
    public MessageDTO message(MessageDTO message) throws Exception {
        return message;
    }
}
