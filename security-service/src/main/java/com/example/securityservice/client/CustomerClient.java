package com.example.securityservice.client;

import com.example.securityservice.dto.request.CustomerRequestDto;
import com.example.securityservice.entity.Customer;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "customer-service", url = "localhost:8080/customer")
public interface CustomerClient {
    @GetMapping("customer-details-request")
    ResponseEntity<Customer> getCustomerDetails(@RequestParam String email);

    @PostMapping
    ResponseEntity<CustomerRequestDto> signUp(@RequestBody @Valid CustomerRequestDto customerRequestDto);
}
