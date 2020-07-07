package com.example.MailService.MQConfig;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface ChannelBinding {
    String EMAILCHANNEL = "emailChannel";

    @Input(EMAILCHANNEL)
    SubscribableChannel greeting();
}
