package com.example.addressservice.model.dto.response;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AddressResponseDto {
    private Long customerId;

    private String street;

    private String state;

    private String postalCode;

    private String country;

    private LocalDateTime creationDate;
}
