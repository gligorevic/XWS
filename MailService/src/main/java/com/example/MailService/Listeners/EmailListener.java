package com.example.MailService.Listeners;

import com.example.MailService.MQConfig.ChannelBinding;
import com.example.MailService.Service.EmailService;
import com.example.MailService.dto.EmailMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;

@EnableBinding(ChannelBinding.class)
public class EmailListener {

    @Autowired
    private EmailService emailService;

    @StreamListener(target = ChannelBinding.EMAILCHANNEL)
    public void processEmailChannelMessage(EmailMessage message) {
        System.out.println(message.getMessage() + " " + message.getMailTo());
        try {
            emailService.sendSimpleMessage(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
