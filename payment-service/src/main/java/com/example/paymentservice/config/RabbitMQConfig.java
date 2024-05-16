package com.example.paymentservice.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration

public class RabbitMQConfig {


    @Bean
    public Queue paymentSuccessQueue() {
        return new Queue("payment-success-queue");
    }

    @Bean
    public TopicExchange paymentExchange() {
        return new TopicExchange("payment-exchange");
    }

    @Bean
    public Binding binding() {
        return BindingBuilder
                .bind(paymentSuccessQueue())
                .to(paymentExchange())
                .with("payment-success");
    }

}
