package com.example.paymentservice.model.mapper;

import com.example.paymentservice.model.dto.request.BalanceRequestDto;
import com.example.paymentservice.model.dto.response.BalanceResponseDto;
import com.example.paymentservice.model.entity.Balance;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface BalanceMapper {
    Balance mapToEntity(BalanceRequestDto balanceRequestDto, Long customerId);

    BalanceResponseDto mapToDto(Balance lastBalance);
}
