package com.example.reservationservice.controller;

import com.example.reservationservice.exception.CustomerNotFoundException;
import com.example.reservationservice.exception.ReservationException;
import com.example.reservationservice.model.dto.request.ReservationRequestDto;
import com.example.reservationservice.model.dto.request.ReservationUpdateRequestDto;
import com.example.reservationservice.model.dto.response.ReservationResponseDto;
import com.example.reservationservice.service.ReservationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("reservations")
@RequiredArgsConstructor
public class ReservationController {
    private final ReservationService reservationService;

    @PostMapping
    public ResponseEntity<ReservationRequestDto> createReservation(@RequestParam Long roomId, Long customerId,
                                                                   @RequestBody @Valid ReservationRequestDto requestDto) throws ReservationException, CustomerNotFoundException {
        return new ResponseEntity<>(reservationService.createReservation(requestDto, roomId, customerId), HttpStatus.CREATED);
    }

    @GetMapping()
    public ResponseEntity<List<ReservationResponseDto>> getAllReservations() {
        return new ResponseEntity<>(reservationService.getAllReservations(), HttpStatus.OK);
    }

    @GetMapping("{reservationId}")
    public ResponseEntity<ReservationResponseDto> getReservationById(@PathVariable Long reservationId) throws ReservationException {
        return new ResponseEntity<>(reservationService.getReservationById(reservationId), HttpStatus.OK);
    }

    @GetMapping("/all-reservations/{customerId}")
    public ResponseEntity<List<ReservationResponseDto>> getReservationByCustomerId(@PathVariable Long customerId) throws CustomerNotFoundException {
        return new ResponseEntity<>(reservationService.getReservationByCustomerId(customerId), HttpStatus.OK);

    }

    @GetMapping("/only-active-reservations/{customerId}")
    public ResponseEntity<List<ReservationResponseDto>> getAnyReservationByCustomerId(@PathVariable Long customerId) throws CustomerNotFoundException {
        return new ResponseEntity<>(reservationService.getAnyReservationByCustomerId(customerId), HttpStatus.OK);
    }

    @GetMapping("/only-inactive-reservations/{customerId}")
    public ResponseEntity<List<ReservationResponseDto>> getInActiveReservationByCustomerId(@PathVariable Long customerId) throws CustomerNotFoundException {
        return new ResponseEntity<>(reservationService.getInActiveReservationByCustomerId(customerId), HttpStatus.OK);
    }

    @DeleteMapping("all-inactive/{customerId}")
    public ResponseEntity<String> deleteReservationByCustomerId(@RequestParam Long customerId) throws CustomerNotFoundException, ReservationException {
        reservationService.deleteByCustomerId(customerId);

        return ResponseEntity.ok("Success");
    }

    @DeleteMapping("any-inactive/{reservationId}")
    public ResponseEntity<String> deleteReservationById(@RequestParam Long reservationId) throws ReservationException {
        reservationService.deleteReservationById(reservationId);

        return ResponseEntity.ok("Success");
    }

    @PutMapping("{id}")
    public ResponseEntity<String> updateReservationById(@RequestParam Long id, Long customerId, @RequestBody ReservationUpdateRequestDto updateRequestDto) throws ReservationException {
        reservationService.updateReservationById(id, customerId, updateRequestDto);
        return ResponseEntity.ok("Success");
    }


}
