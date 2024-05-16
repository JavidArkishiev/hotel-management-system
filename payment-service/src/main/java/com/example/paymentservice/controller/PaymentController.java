package com.example.paymentservice.controller;

import com.example.paymentservice.model.dto.request.PaymentDto;
import com.example.paymentservice.model.entity.Payment;
import com.example.paymentservice.service.PaymentService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("payment")
@RestController
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;

    @Transactional
    @PostMapping("pay")
    public ResponseEntity<String> doPayment(@RequestBody PaymentDto paymentDto,
                                            @RequestParam Long reservationId, @RequestParam Long customerId) {
        paymentService.makePayment(paymentDto, reservationId, customerId);
        return ResponseEntity.ok("Success");
    }

}
