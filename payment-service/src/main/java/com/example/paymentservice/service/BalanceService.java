package com.example.paymentservice.service;

import com.example.paymentservice.client.CustomerServiceClient;
import com.example.paymentservice.exception.BalanceException;
import com.example.paymentservice.model.dto.request.BalanceRequestDto;
import com.example.paymentservice.model.dto.response.BalanceResponseDto;
import com.example.paymentservice.model.dto.response.CustomerResponseDto;
import com.example.paymentservice.model.entity.Balance;
import com.example.paymentservice.model.mapper.BalanceMapper;
import com.example.paymentservice.repository.BalanceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class BalanceService {
    private final BalanceRepository balanceRepository;
    private final BalanceMapper balanceMapper;
    private final CustomerServiceClient customerServiceClient;

    public BalanceRequestDto topUp(BalanceRequestDto requestDto, Long customerId) {

        if (requestDto.getAmount().compareTo(BigDecimal.ZERO) == 0) {
            throw new BalanceException("amount can not be Zero");
        }
        if (!Objects.requireNonNull(customerServiceClient.getCustomerById(customerId).getBody()).isEnabled()) {
            throw new BalanceException("customer is not active");
        }

        CustomerResponseDto responseDto = customerServiceClient.getCustomerById(customerId).getBody();
        if (responseDto != null) {
            List<Balance> balanceList = balanceRepository.findByCustomerIdOrderByCreationDateDesc(customerId);
            if (!balanceList.isEmpty()) {
                Balance lastBalance = balanceList.get(0);
                BigDecimal newBalance = lastBalance.getBalance().add(requestDto.getAmount());
                Balance balance = new Balance();
                balance.setAmount(requestDto.getAmount());
                balance.setCreationDate(LocalDateTime.now());
                balance.setCustomerId(customerId);
                balance.setBalance(newBalance);
                balanceRepository.save(balance);
            } else {
                Balance balance = new Balance();
                balance.setCreationDate(LocalDateTime.now());
                balance.setAmount(requestDto.getAmount());
                balance.setBalance(requestDto.getAmount());
                balance.setCustomerId(customerId);
                balanceRepository.save(balance);

            }
            return requestDto;
        }
        return null;
    }


    public BalanceResponseDto getBalanceByCustomerId(Long customerId) {
        List<Balance> balanceList = balanceRepository.findByCustomerIdOrderByCreationDateDesc(customerId);
        if (balanceList.isEmpty()) {
            throw new BalanceException("can not found a balance with this customerId");
        }
        Balance lastBalance = balanceList.get(0);
        return balanceMapper.mapToDto(lastBalance);
    }

    public BalanceResponseDto getBalanceByBalanceId(Long id) {
        Balance balanceEntity = balanceRepository.findById(id)
                .orElseThrow(() -> new BalanceException("can not found a balance with this balanceId"));
        return balanceMapper.mapToDto(balanceEntity);
    }
}
