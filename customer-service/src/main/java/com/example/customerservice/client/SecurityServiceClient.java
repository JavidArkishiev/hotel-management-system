package com.example.customerservice.client;

import com.example.customerservice.model.dto.request.CustomerRequestDto;
import com.example.customerservice.model.entity.Customer;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "security-service", url = "localhost:8098/auth")
public interface SecurityServiceClient {
    @PostMapping("update-account-status")
    ResponseEntity<String> updateAccountStatus(@RequestParam Long customerId, @RequestParam String otp);

    @PostMapping("/saveCustomer")
    ResponseEntity<Customer> saveCustomer(@RequestBody CustomerRequestDto signUpRequest);
}
