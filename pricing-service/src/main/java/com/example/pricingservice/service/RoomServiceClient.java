package com.example.pricingservice.service;

import com.example.pricingservice.model.dto.response.RoomResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "room-service", url = "localhost:8084/room")
public interface RoomServiceClient {
    @GetMapping("{id}")
    ResponseEntity<RoomResponseDto> getRoomById(@PathVariable Long id);


}
