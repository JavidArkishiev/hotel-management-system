package com.example.pricingservice.controller;

import com.example.pricingservice.exception.RoomException;
import com.example.pricingservice.model.dto.request.PriceRequestDto;
import com.example.pricingservice.model.dto.response.PriceResponseDto;
import com.example.pricingservice.service.PriceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("price")
@RequiredArgsConstructor
public class PriceController {
    private final PriceService priceService;

    @PostMapping
    public ResponseEntity<PriceRequestDto> createPrice(@RequestParam Long roomId,@RequestBody @Valid
                                                       PriceRequestDto requestDto) throws RoomException {
        return new ResponseEntity<>(priceService.createPrice(requestDto,roomId), HttpStatus.CREATED);
    }

    @GetMapping("{roomId}")
    public ResponseEntity<PriceResponseDto> getPriceByRoomId(@PathVariable Long roomId) {
        return new ResponseEntity<>(priceService.getPriceByRoomId(roomId), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<PriceResponseDto>> getAllPrice() {
        return new ResponseEntity<>(priceService.getAllPrice(), HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deletePriceById(@PathVariable Long id) {
        priceService.deletePriceById(id);
        return ResponseEntity.ok("SUCCESS");
    }

    @PutMapping("{roomId}")
    public ResponseEntity<PriceRequestDto> updatePriceByRoomId(@PathVariable Long roomId,
                                                               @RequestBody @Valid PriceRequestDto requestDto) throws RoomException {
        return new ResponseEntity<>(priceService.updatePriceByRoomId(roomId, requestDto),HttpStatus.OK);


    }

}
