package com.example.reviewservice.client;

import com.example.reviewservice.model.dto.responce.CustomerResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
@FeignClient(name = "customer-service", url = "localhost:8080/customer")
public interface CustomerServiceClient {
    @GetMapping("{customerId}")
    ResponseEntity<CustomerResponseDto> getCustomerById(@RequestParam Long customerId);

}
