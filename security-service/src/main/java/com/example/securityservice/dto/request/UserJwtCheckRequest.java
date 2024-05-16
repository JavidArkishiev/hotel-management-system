package com.example.securityservice.dto.request;

import com.example.securityservice.entity.Customer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

//import javax.validation.constraints.NotBlank

@Data
public class UserJwtCheckRequest {

    @NotBlank
    private String jwt;

}