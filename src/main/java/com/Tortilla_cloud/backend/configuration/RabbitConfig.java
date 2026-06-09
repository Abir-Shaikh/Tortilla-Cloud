package com.Tortilla_cloud.backend.configuration;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;

@Configuration
public class RabbitConfig {

    //queue names
    public static final String ORDER_QUEUE = "order.queue";
    public static final String ORDER_EXCHANGE = "order.exchange";
    public static final String ORDER_ROUTING_KEY = "order.#";

    //create queue
    //here the messages will be stored
    @Bean
    public Queue orderQueue(){
        return new Queue(ORDER_QUEUE , true);
    }

    //create exchange
    //here we receive messages and routes them to queues
    @Bean
    public TopicExchange orderExchange(){
        return new TopicExchange(ORDER_EXCHANGE, true , false);
    }

    //bind queue to exchange
    //connects queue to the exchange with a routing key
    @Bean
    public Binding binding(Queue orderQueue , TopicExchange orderExchange){
        return BindingBuilder.bind(orderQueue)
                .to(orderExchange)
                .with(ORDER_ROUTING_KEY);
    }

    // Use JSON instead of Java serialization (avoids deserialization allow-list errors and is safer).
    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory, MessageConverter messageConverter) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(messageConverter);
        return template;
    }

    // Ensures @RabbitListener uses the same JSON converter.
    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(
            ConnectionFactory connectionFactory,
            MessageConverter messageConverter
    ) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(messageConverter);
        return factory;
    }
}
