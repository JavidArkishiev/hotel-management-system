package com.example.securityservice.service;

import com.example.securityservice.dto.request.CustomerRequestDto;
import com.example.securityservice.dto.request.RefreshTokenRequest;
import com.example.securityservice.dto.request.SignInRequest;
import com.example.securityservice.dto.request.UserJwtCheckRequest;
import com.example.securityservice.dto.response.JwtAuthenticationResponse;
import com.example.securityservice.dto.response.JwtCheckResponse;
import com.example.securityservice.entity.Customer;

public interface AuthenticationService {
    Customer saveCustomer(CustomerRequestDto signUpRequest);

    JwtAuthenticationResponse sigIn(SignInRequest sign);

    JwtAuthenticationResponse refreshToken(RefreshTokenRequest request);

    void updateAccountStatus(Long customerId, String otp);

    JwtCheckResponse validateJwt(UserJwtCheckRequest request);

    CustomerRequestDto updateCustomer(Long customerId, CustomerRequestDto dto);
}
