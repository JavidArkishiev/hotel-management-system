package com.example.notificationservice.controller;

import com.example.notificationservice.model.dto.SignUpEmailRequest;
import com.example.notificationservice.service.NotificationService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;


    @PostMapping("/send-signUp-notification")
    public ResponseEntity<String> sendSignUpVerificationNotify(@RequestParam Long customerId) throws MessagingException {
        notificationService.sendVerificationEmail(customerId);

        return ResponseEntity.ok("Email notification sent successfully.");
    }

    @PostMapping("verify")
    public ResponseEntity<String> verifyUser(@RequestParam String otp, @RequestParam Long customerId) {
        notificationService.verify(customerId, otp);
        return ResponseEntity.ok("Success.Your account has activated. You can login a website");

    }

}
