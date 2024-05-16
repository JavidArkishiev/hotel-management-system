package com.example.paymentservice.controller;

import com.example.paymentservice.model.dto.request.BalanceRequestDto;
import com.example.paymentservice.model.dto.response.BalanceResponseDto;
import com.example.paymentservice.service.BalanceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("balance")
@RequiredArgsConstructor
public class BalanceController {
    private final BalanceService balanceService;

    @PostMapping
    public ResponseEntity<BalanceRequestDto> topUp(@RequestBody @Valid BalanceRequestDto balanceRequestDto,
                                                   @RequestParam Long customerId) {
        return new ResponseEntity<>(balanceService.topUp(balanceRequestDto, customerId), HttpStatus.CREATED);
    }

    @GetMapping("{customerId}")
    public ResponseEntity<BalanceResponseDto> getBalanceByCustomerId(@PathVariable Long customerId) {
        return new ResponseEntity<>(balanceService.getBalanceByCustomerId(customerId), HttpStatus.OK);
    }

    @GetMapping("balance-id/{id}")
    public ResponseEntity<BalanceResponseDto> getBalanceByBalanceId(@PathVariable Long id) {
        return new ResponseEntity<>(balanceService.getBalanceByBalanceId(id), HttpStatus.OK);
    }

}
