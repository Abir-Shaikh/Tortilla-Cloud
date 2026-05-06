package com.Tortilla_cloud.service.messaging.consumer;

import com.Tortilla_cloud.DTO.OrderMessage;
import com.Tortilla_cloud.configuration.RabbitConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class OrderListener {

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

        //background processing
        simulateOrderProcessing(message);
    }

    //simulate what the background worker does
    private void simulateOrderProcessing(OrderMessage message) {
        try {
            log.info("Starting order processing for order: {}" , message.getOrderId());

            Thread.sleep(2000);

            log.info("Order #{} processed successfully" , message.getOrderId());
            log.info("Confirmation email sent to: {}" , message.getCustomerEmail());
            log.info("Inventory updated");
        }
        catch (InterruptedException e){
            log.error("Error processing order" , e);
            Thread.currentThread().interrupt();
        }
    }
}
