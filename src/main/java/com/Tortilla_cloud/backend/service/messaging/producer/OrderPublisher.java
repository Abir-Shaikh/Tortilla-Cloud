package com.Tortilla_cloud.backend.service.messaging.producer;

import com.Tortilla_cloud.backend.DTO.OrderMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class OrderPublisher {

    @Autowired
    @Qualifier("orderInputChannel")
    private MessageChannel orderInputChannel;

    private final RabbitTemplate rabbitTemplate;

    public OrderPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    //sending order message to queue
    public void publishOrderMessage(OrderMessage msg){

        Message<OrderMessage> message = MessageBuilder
                .withPayload(msg)
                .setHeader("publishedTime", System.currentTimeMillis())
                .build();
        orderInputChannel.send(message);
    }
}
