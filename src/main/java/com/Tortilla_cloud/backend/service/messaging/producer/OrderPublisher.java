package com.Tortilla_cloud.backend.service.messaging.producer;

import com.Tortilla_cloud.backend.DTO.OrderMessage;
import com.Tortilla_cloud.backend.configuration.RabbitConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class OrderPublisher {
    private final RabbitTemplate rabbitTemplate;

    public OrderPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    //sending order message to queue
    public void publishOrderMessage(OrderMessage message){
        log.info("Publishing order message for Order ID: {}" , message.getOrderId());


        rabbitTemplate.convertAndSend(
                RabbitConfig.ORDER_EXACHANGE ,
                // Send with a concrete routing key; the queue is bound with "order.#".
                "order.created" ,
                message
        );


        log.info("Order message published successfully");
    }
}
