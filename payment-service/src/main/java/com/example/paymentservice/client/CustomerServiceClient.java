package com.example.paymentservice.client;

import com.example.paymentservice.model.dto.response.CustomerResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "customer-service", url = "localhost:8080/customer")
public interface CustomerServiceClient {
    @GetMapping("{customerId}")
    ResponseEntity<CustomerResponseDto> getCustomerById(@RequestParam Long customerId);

}
