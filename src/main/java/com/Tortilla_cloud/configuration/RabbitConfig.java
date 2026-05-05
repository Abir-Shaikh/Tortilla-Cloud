package com.Tortilla_cloud.configuration;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;

public class RabbitConfig {

    //queue names
    public static final String ORDER_QUEUE = "order.queue";
    public static final String ORDER_EXACHANGE = "order.exchange";
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
        return new TopicExchange(ORDER_EXACHANGE , true , false);
    }

    //bind queue to exchange
    //connects queue to the exchange with a routing key
    public Binding binding(Queue orderQueue , TopicExchange orderExchange){
        return BindingBuilder.bind(orderQueue)
                .to(orderExchange)
                .with(ORDER_ROUTING_KEY);
    }
}
