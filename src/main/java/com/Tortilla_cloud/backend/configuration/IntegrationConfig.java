package com.Tortilla_cloud.backend.configuration;

import com.Tortilla_cloud.backend.DTO.OrderMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.messaging.MessageChannel;

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

    //take input -> validate -> route -> output
    @Bean
    public IntegrationFlow orderProcessingFlow(){
        return IntegrationFlow
                .from("orderInputChannel")
                .filter(message -> isOrderValid(message),
                        e -> e.discardChannel("invalidOrderChannel"))//validate
                .channel("validOrderChannel")
                .get();
    }

    //same as validation but here we just have to print smth
    @Bean
    public IntegrationFlow invalidOrderFlow(){
        return IntegrationFlow
                .from("orderInputChannel")
                .filter(message -> !isOrderValid(message))
                .channel("invalidOrderChannel")
                .handle(msg -> System.out.println("Inavalid : " + msg.getPayload()))
                .get();
    }

    private boolean isOrderValid(Object message) {

        //checking validation
        if (!(message instanceof OrderMessage)) {
            log.warn("order is null");
            return false;
        }

        OrderMessage order = (OrderMessage) message;

        if (order.getOrderId() == null) {
            log.warn("Missing order Id");
            return false;
        }
        return true;
    }
}
