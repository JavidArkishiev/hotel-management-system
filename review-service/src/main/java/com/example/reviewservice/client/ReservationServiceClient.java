package com.example.reviewservice.client;

import com.example.reviewservice.model.dto.responce.ReservationResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "reservation-service", url = "localhost:8082/reservations")

public interface ReservationServiceClient {
    @GetMapping("{reservationId}")
    ResponseEntity<ReservationResponseDto> getReservationById(@PathVariable Long reservationId);

}
