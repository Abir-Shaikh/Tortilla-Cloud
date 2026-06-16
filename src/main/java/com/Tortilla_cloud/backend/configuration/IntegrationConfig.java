package com.Tortilla_cloud.backend.configuration;

import com.Tortilla_cloud.backend.DTO.OrderMessage;
import com.Tortilla_cloud.backend.service.messaging.consumer.OrderListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;

@Slf4j
@Configuration
@EnableIntegration
public class IntegrationConfig {

    //where order comes in
    @Bean(name = "orderInputChannel")
    public MessageChannel orderInputChannel(){
        return new DirectChannel();
    }

    //valid orders / after validation
    @Bean(name = "validOrderChannel")
    public MessageChannel validOrderChannel(){
        return new DirectChannel();
    }

    //invalid orders
    @Bean(name = "invalidOrderChannel")
    public MessageChannel invalidOrderChannel(){
        return new DirectChannel();
    }

    @Bean(name = "errorChannel")
    public MessageChannel errorChannel(){
        return new DirectChannel();
    }

    //Flow -> take input -> validate -> route -> output
    @Bean
    public IntegrationFlow orderProcessingFlow(){
        return IntegrationFlow
                .from("orderInputChannel")
                .filter(message -> isOrderValid(message),
                        e -> e.discardChannel("invalidOrderChannel"))//validate
                .channel("validOrderChannel")
                .get();
    }

    //valid Order Handler -> handles valid orders

    @Bean
    @ServiceActivator(inputChannel = "validOrderChannel")
    public MessageHandler orderHandler(OrderListener orderListener){
        return message -> {
            try {
                OrderMessage order = (OrderMessage) message.getPayload();
                log.info("Integration processing Order: {}" , order.getOrderId());
                orderListener.simulateOrderProcessing(order);

                log.info("Integration Completed order: {}", order.getOrderId());
            } catch (Exception e) {
                log.error("Handler Error: ", e);
                throw new MessagingException(message , e);
            }
        };
    }

    //invalid order handler -> handles invalid order

    @Bean
    @ServiceActivator(inputChannel = "invalidOrderChannel")
    public MessageHandler invalidOrderHandler(){
        return message -> {
            OrderMessage order = (OrderMessage) message.getPayload();
            log.warn("Invalid Order Rejected: {}", order.getOrderId());
        };
    }

    //error handler -> handles errors

    @Bean
    @ServiceActivator(inputChannel = "errorChannel")
    public MessageHandler errorHandler(){
        return message -> {
            log.error("Integration Error: {}", message.getPayload());
        };
    }


    //validation logic
    private boolean isOrderValid(Object messageObj) {
        try {
            Message<?> message = (Message<?>)  messageObj;
            OrderMessage order = (OrderMessage) message.getPayload();

            if (order.getOrderId() == null) {
                log.warn("Invalid: Missing Order Id");
                return false;
            }
            if (order.getCustomerEmail() == null || order.getCustomerEmail().isEmpty()) {
                log.warn("Invalid! Missing Email");
                return false;
            }
            return true;
        }
        catch (Exception e){
            log.error("Validation Error", e);
            return false;
        }
    }
}



//        //checking validation
//        if (!(message instanceof OrderMessage)) {
//            log.warn("order is null");
//            return false;
//        }
//
//        OrderMessage order = (OrderMessage) message;
//
//        if (order.getOrderId() == null) {
//            log.warn("Missing order Id");
//            return false;
//        }
//        return true;
//    }
