package com.example.paymentservice.pruducer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class RabbitMQProducer {
    private final RabbitTemplate rabbitTemplate;

    public RabbitMQProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendQueue(Long reservationId) {
        log.info("reservationId -> {}", reservationId);
        rabbitTemplate.convertAndSend("payment-exchange", "payment-success", reservationId);

    }
}
