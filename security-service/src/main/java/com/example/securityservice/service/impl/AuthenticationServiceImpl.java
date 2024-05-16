package com.example.securityservice.service.impl;

import com.example.securityservice.client.CustomerClient;
import com.example.securityservice.dto.request.CustomerRequestDto;
import com.example.securityservice.dto.request.RefreshTokenRequest;
import com.example.securityservice.dto.request.SignInRequest;
import com.example.securityservice.dto.request.UserJwtCheckRequest;
import com.example.securityservice.dto.response.JwtAuthenticationResponse;
import com.example.securityservice.dto.response.JwtCheckResponse;
import com.example.securityservice.entity.Customer;
import com.example.securityservice.exception.UserNotfoundException;
import com.example.securityservice.mapper.CustomerMapper;
import com.example.securityservice.repository.CustomerRepository;
import com.example.securityservice.service.AuthenticationService;
import com.example.securityservice.service.JWTService;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final CustomerRepository customerRepository;
    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;
    private final CustomerClient customerClient;
    private final CustomerMapper customerMapper;

    @Override
    public Customer saveCustomer(CustomerRequestDto signUpRequest) {
//        customerClient.saveCustomer(signUpRequest).getBody();
        Customer customer = customerClient.getCustomerDetails(signUpRequest.getEmail()).getBody();
        if (customerRepository.existsByEmail(signUpRequest.getEmail())) {
            throw new UserNotfoundException("This email already exist");
        }
        assert customer != null;
        return customerRepository.save(customer);
    }

    public JwtAuthenticationResponse sigIn(SignInRequest sign) {
        var customer = customerRepository.findByEmail(sign.getEmail())
//        Customer customer = customerClient.getCustomerDetails(sign.getEmail()).getBody();

                .orElseThrow(() -> new UserNotfoundException("User not found"));

        if (!customer.isEnabled()) {

            throw new UserNotfoundException("your account is not active. Please first of all verify your account");
        }
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(sign.getEmail(), sign.getPassword()));
        } catch (AuthenticationException e) {
            throw new UserNotfoundException("Invalid email or password");
        }
        var jwt = jwtService.generateToken(customer);
        var refreshToken = jwtService.generateRefreshToken(new HashMap<>(), customer);

        return JwtAuthenticationResponse.builder().accessToken(jwt).refreshToken(refreshToken).build();
    }

    public JwtAuthenticationResponse refreshToken(RefreshTokenRequest request) {
        String userEmail = jwtService.extractUsername(request.getToken());
        Customer customer = customerRepository.findByEmail(userEmail).orElseThrow();
//        Customer customer = customerClient.getCustomerDetails(userEmail).getBody();

        if (jwtService.isTokenValid(request.getToken(), customer)) {
            var jwt = jwtService.generateToken(customer);

            return JwtAuthenticationResponse.builder().accessToken(jwt).refreshToken(request.getToken()).build();
        }
        return null;

    }

    @Override
    public void updateAccountStatus(Long customerId, String otp) {
        Customer customer = customerRepository.findByCustomerId(customerId)
                .orElseThrow(() -> new UserNotfoundException("User not found :" + customerId));

        customer.setEnabled(true);
        customerRepository.save(customer);

    }

    public CustomerRequestDto updateCustomer(Long customerId, CustomerRequestDto dto) {
        Customer oldCustomer = customerRepository.findByCustomerId(customerId).
                orElseThrow(() -> new UserNotfoundException("user not found"));
        if (oldCustomer != null) {
            Customer updateCustomer = customerMapper.mapToUpdateEntity(dto, oldCustomer);
            updateCustomer.setCustomerId(oldCustomer.getCustomerId());
            customerRepository.save(updateCustomer);

        }
        return dto;
    }

    @Override
    public JwtCheckResponse validateJwt(UserJwtCheckRequest request) {
        if (request == null || StringUtils.isBlank(request.getJwt()))
            throw new UserNotfoundException("null token");

        if (!jwtService.validate(request.getJwt())) {
            throw new UserNotfoundException("token is not valid");
        }
        var userEmail = jwtService.extractUsername(request.getJwt());

        return JwtCheckResponse.builder()
                .email(userEmail)
                .build();
    }

}





