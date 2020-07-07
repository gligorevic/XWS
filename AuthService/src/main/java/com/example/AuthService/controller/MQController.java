package com.example.AuthService.controller;

import com.example.AuthService.MQConfig.ChannelBinding;
import com.example.AuthService.dto.EmailMessage;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MQController {
    private MessageChannel email;

    public MQController(ChannelBinding channelBinding) {
        this.email = channelBinding.mailing();
    }

    @GetMapping("/greet/{name}")
    public void publish(@PathVariable String name) {

        Message<EmailMessage> msg = MessageBuilder.withPayload(new EmailMessage("igor.gligorevic@hotmail.com", "Proba", name)).build();
        this.email.send(msg);
    }
}
