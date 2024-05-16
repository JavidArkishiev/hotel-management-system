package com.example.addressservice.model.dto.request;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AddressRequestDto {

    @NotBlank(message = "street can not be null")
    private String street;

    @NotBlank(message = "state can not be null")
    private String state;

    @NotBlank(message = "postalCode can not be null")
    private String postalCode;

    @NotBlank(message = "country can not be null")
    private String country;

}
