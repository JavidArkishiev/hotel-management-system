package com.example.notificationservice.client;

import com.example.notificationservice.model.dto.SignUpEmailRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "customer-service", url = "localhost:8080/customer")
public interface CustomerServiceClient {
    @GetMapping("sign-up-request")
    ResponseEntity<SignUpEmailRequest> getSignUpEmailRequestByCustomerId(@RequestParam Long customerId);

    @PostMapping("update-account-status")
    ResponseEntity<String> updateAccountStatus(@RequestParam Long customerId, @RequestParam String otp);

}
