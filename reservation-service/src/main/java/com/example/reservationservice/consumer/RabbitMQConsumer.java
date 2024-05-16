package com.example.reservationservice.consumer;

import com.example.reservationservice.exception.ReservationException;
import com.example.reservationservice.service.ReservationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class RabbitMQConsumer {

    private final ReservationService reservationService;

    public RabbitMQConsumer(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @RabbitListener(queues = "${payment.queue.name}")
    public void processReservationMessage(Long reservationId) throws ReservationException {
        log.info("received-> {}", reservationId.toString());
        reservationService.updateReservationStatus(reservationId);
    }
}
