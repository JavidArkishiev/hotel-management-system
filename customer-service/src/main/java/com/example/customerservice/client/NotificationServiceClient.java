package com.example.customerservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "notification-service", url = "localhost:8094/notifications")
public interface NotificationServiceClient {
    @PostMapping("/send-signUp-notification")
    ResponseEntity<String> sendSignUpVerificationNotify(@RequestParam Long customerId);

}
