package com.example.FeedbackService.MQConfig;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface ChannelBinding {
    @Output("emailChannel")
    MessageChannel mailing();
}
