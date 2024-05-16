package com.example.paymentservice.service;

import com.example.paymentservice.client.CustomerServiceClient;
import com.example.paymentservice.client.ReservationServiceClient;
import com.example.paymentservice.config.RabbitMQConfig;
import com.example.paymentservice.exception.BalanceException;
import com.example.paymentservice.model.dto.request.PaymentDto;
import com.example.paymentservice.model.dto.response.CustomerResponseDto;
import com.example.paymentservice.model.dto.response.ReservationResponseDto;
import com.example.paymentservice.model.entity.Balance;
import com.example.paymentservice.model.entity.Payment;
import com.example.paymentservice.pruducer.RabbitMQProducer;
import com.example.paymentservice.repository.BalanceRepository;
import com.example.paymentservice.repository.PaymentRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
@Slf4j
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final BalanceRepository balanceRepository;
    private final CustomerServiceClient customerServiceClient;
    private final ReservationServiceClient reservationServiceClient;
    private final RabbitMQProducer producer;

    public void makePayment(PaymentDto paymentDto, Long reservationId, Long customerId) {

        if (!Objects.requireNonNull(customerServiceClient.getCustomerById(customerId).getBody()).isEnabled()) {
            throw new BalanceException("customer is not active");
        }

        if (reservationServiceClient.getReservationById(reservationId).getBody().isActive()) {
            throw new BalanceException("this reservation already has payment");
        }

        CustomerResponseDto customerResponseDto = customerServiceClient.getCustomerById(customerId).getBody();
        ReservationResponseDto responseDto = reservationServiceClient.getReservationById(reservationId).getBody();


        if (customerResponseDto == null) {
            throw new RuntimeException("customer can not");
        }
        if (responseDto == null) {
            throw new RuntimeException("reservation not found");
        }

        List<Balance> balanceList = balanceRepository.findByCustomerIdOrderByCreationDateDesc(customerId);
        if (balanceList.isEmpty()) {
            throw new BalanceException("there is not enough money in the balance." +
                    "Please topUp the balance ");
        }

        Balance lastBalance = balanceList.get(0);
        Payment newPayment = new Payment();
        if (paymentDto.getAmount().compareTo(lastBalance.getBalance()) > 0) {
            throw new BalanceException("there is not enough money in the balance. " +
                    "Please topUp the balance");
        }
        if (paymentDto.getAmount().compareTo(Objects.requireNonNull(reservationServiceClient
                .getReservationById(reservationId).getBody()).getTotalPrice()) != 0 ||
                paymentDto.getAmount().compareTo(lastBalance.getBalance()) > 0) {
            throw new BalanceException("amount is not equals with reservationPrice");
        }

        BigDecimal newBalance = lastBalance.getBalance().subtract(paymentDto.getAmount());

        newPayment.setAmount(paymentDto.getAmount());
        newPayment.setReservationId(reservationId);
        newPayment.setPaymentDate(LocalDateTime.now());
        newPayment.setSuccessful(true);
        newPayment.setBalance(lastBalance);
        newPayment.setPaymentMethod(paymentDto.getPaymentMethod());
        paymentRepository.save(newPayment);

        Balance balance = new Balance();
        balance.setBalance(newBalance);
        balance.setAmount(newPayment.getAmount());
        balance.setCustomerId(customerId);
        balance.setPayment(newPayment);
        balanceRepository.save(balance);

        producer.sendQueue(reservationId);


    }
}
