package com.example.MailService.Service;

import com.example.MailService.dto.EmailMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender emailSender;

    public void sendSimpleMessage(EmailMessage emailMessage) {
        System.out.println("Saljem poruku");
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("xws.tim.20@gmail.com");
        message.setTo(emailMessage.getMailTo());
        message.setSubject(emailMessage.getSubject());
        message.setText(emailMessage.getMessage());
        emailSender.send(message);
        System.out.println("Poslao poruku");
    }
}