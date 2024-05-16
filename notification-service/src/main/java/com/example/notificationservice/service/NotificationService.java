
package com.example.notificationservice.service;

import com.example.notificationservice.client.CustomerServiceClient;
import com.example.notificationservice.model.dto.SignUpEmailRequest;
import com.example.notificationservice.model.entity.Notification;
import com.example.notificationservice.repository.NotificationRepository;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final EmailService emailService;
    private final CustomerServiceClient customerServiceClient;
    private final NotificationRepository notificationRepository;

    public void sendVerificationEmail(Long customerId) throws MessagingException {
        SignUpEmailRequest request = customerServiceClient.getSignUpEmailRequestByCustomerId(customerId).getBody();

        String subject = "Email verification";
        String body = "your verification otp is: " + request.getOtp();
        emailService.sendEmail(request.getEmail(), subject, body);

        Notification notification = new Notification();
        notification.setCustomerId(customerId);
        notification.setMessage(subject);
        notification.setEmail(request.getEmail());
        notification.setOpt(request.getOtp());
        notificationRepository.save(notification);
    }

    public void verify(Long customerId, String otp) {
        customerServiceClient.updateAccountStatus(customerId, otp).getBody();


    }
}
