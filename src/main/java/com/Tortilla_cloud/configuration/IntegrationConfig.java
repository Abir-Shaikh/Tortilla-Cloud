package com.Tortilla_cloud.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.MessageChannels;
import org.springframework.messaging.MessageChannel;

@Configuration
@EnableIntegration
public class IntegrationConfig {

    //where order comes in
    @Bean(name = "orderInputChannel")
    public MessageChannel orderInputChannel(){
        return new DirectChannel();
    }

//    @Bean(name = "validOrderChannel")
//    public MessageChannel validOrderChannel(){
//        return MessageChannels.direct().get();
//    }
}
