package com.example.paymentservice.client;

import com.example.paymentservice.model.dto.response.ReservationResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "reservation-service", url = "localhost:8082/reservations")

public interface ReservationServiceClient {
    @GetMapping("{reservationId}")
    ResponseEntity<ReservationResponseDto> getReservationById(@PathVariable Long reservationId);

}
