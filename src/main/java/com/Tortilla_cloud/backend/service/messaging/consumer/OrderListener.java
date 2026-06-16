package com.Tortilla_cloud.backend.service.messaging.consumer;

import com.Tortilla_cloud.backend.DTO.OrderMessage;
import com.Tortilla_cloud.backend.configuration.RabbitConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class OrderListener {

    @Autowired
    private MessageChannel orderInputChannel;

    //this method calls automatically when a message arrives don't need to write any loop statement
    @RabbitListener(
            queues = RabbitConfig.ORDER_QUEUE,
            ackMode = "#{T(org.springframework.amqp.core.AcknowledgeMode).AUTO}"
    )
    public void processOrderMessage(OrderMessage message){
        log.info("========================================");
        log.info("RECEIVED ORDER MESSAGE FROM QUEUE");
        log.info("========================================");
        log.info("Order ID: {}" , message.getOrderId());
        log.info("Customer: {}" , message.getCustomerName());
        log.info("Email: {}" , message.getCustomerEmail());
        log.info("Delivery: {} , {}" , message.getDeliveryCity() , message.getDeliveryState());
        log.info("Timestamp: {}" , message.getTimestamp());
        log.info("========================================");

        Message<OrderMessage> integrationMessage = MessageBuilder
                .withPayload(message)
                .setHeader("timestamp", System.currentTimeMillis())
                .setHeader("orderSource", "rabbitmq")
                .build();

        orderInputChannel.send(integrationMessage);
        log.info("Order sent to integration channel: {}", message.getOrderId());
    }

    //simulate what the background worker does
    public void simulateOrderProcessing(OrderMessage message) {
        try {
            log.info("Starting order processing for order: {}" , message.getOrderId());
            System.out.println("STDOUT: Starting order processing for order: " + message.getOrderId());

            Thread.sleep(2000);

            log.info("Order #{} processed successfully" , message.getOrderId());
            log.info("Confirmation email sent to: {}" , message.getCustomerEmail());
            log.info("Inventory updated");
            System.out.println("STDOUT: Order processed for order: " + message.getOrderId());
        }
        catch (Exception e){
            log.error("simulateOrderProcessing failed" , e);
            // Re-interrupt only when applicable; keeps original behavior for InterruptedException.
            if (e instanceof InterruptedException) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
