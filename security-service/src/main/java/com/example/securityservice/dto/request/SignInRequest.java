package com.example.securityservice.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SignInRequest {
    @NotBlank(message = "email can not be null")
    private String email;
    @NotBlank(message = "password can not be null")
    private String password;
}
