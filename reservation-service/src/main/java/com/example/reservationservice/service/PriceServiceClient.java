package com.example.reservationservice.service;

import com.example.reservationservice.model.dto.response.PriceDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "pricing-service", url = "localhost:8086/price")
public interface PriceServiceClient {
    @GetMapping("{roomId}")
    ResponseEntity<PriceDto> getPriceByRoomId(@PathVariable Long roomId);

}
