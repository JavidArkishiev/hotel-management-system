package com.example.customerservice.service;

import com.example.customerservice.client.NotificationServiceClient;
import com.example.customerservice.client.SecurityServiceClient;
import com.example.customerservice.exception.ExistEmailException;
import com.example.customerservice.exception.UserNotfoundException;
import com.example.customerservice.model.dto.request.CustomerRequestDto;
import com.example.customerservice.model.dto.request.SignUpEmailRequest;
import com.example.customerservice.model.dto.response.CustomerResponseDto;
import com.example.customerservice.model.entity.Customer;
import com.example.customerservice.model.mapper.CustomerMapper;
import com.example.customerservice.repository.CustomerRepository;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;
    private final SecurityServiceClient securityServiceClient;
    private final NotificationServiceClient notificationServiceClient;

    public CustomerRequestDto signUp(CustomerRequestDto customerRequestDto) throws ExistEmailException, MessagingException {
        if (customerRepository.existsByEmail(customerRequestDto.getEmail())) {
            throw new ExistEmailException("this email already used");
        }
        if (customerRepository.existsByPhoneNumber(customerRequestDto.getPhoneNumber())) {
            throw new ExistEmailException("this phone number already exist");
        }

        Customer customerEntity = customerMapper.mapToEntity(customerRequestDto);
        customerRepository.save(customerEntity);
        securityServiceClient.saveCustomer(customerRequestDto).getBody();
        notificationServiceClient.sendSignUpVerificationNotify(customerEntity.getCustomerId()).getBody();

        return customerRequestDto;
    }

    public CustomerResponseDto getCustomerById(Long customerId) {
        Customer customerEntity = customerRepository.findById(customerId)
                .orElseThrow(() -> new UserNotfoundException("user not found"));
        return customerMapper.mapToDto(customerEntity);

    }

    public List<CustomerResponseDto> getAllCustomer() {
        List<Customer> listCustomer = customerRepository.findAll();
        return listCustomer.stream().map(customerMapper::mapToDto).toList();
    }

    public void deleteCustomerById(Long customerId) {
        Customer customerEntity = customerRepository.findById(customerId)
                .orElseThrow(() -> new UserNotfoundException("user not found"));
        customerRepository.delete(customerEntity);
    }

    public CustomerRequestDto updateCustomer(Long customerId, CustomerRequestDto dto) {
        Customer oldCustomer = customerRepository.findById(customerId).
                orElseThrow(() -> new UserNotfoundException("user not found"));
        if (oldCustomer != null) {
            Customer updateCustomer = customerMapper.mapToUpdateEntity(dto, oldCustomer);
            updateCustomer.setCustomerId(oldCustomer.getCustomerId());
            customerRepository.save(updateCustomer);

        }
        return dto;
    }

    public SignUpEmailRequest getSignUpEmailRequestByCustomerId(Long customerId) {
        Customer customerEntity = customerRepository.findById(customerId)
                .orElseThrow(() -> new UserNotfoundException("user not found"));
        return customerMapper.mapToSignUpEmailRequest(customerEntity);
    }

    public Customer getCustomerDetails(String email) {
        return customerRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotfoundException("user not found"));
    }

    public void updateAccountStatus(Long customerId, String otp) {
        Customer customer = customerRepository.findByCustomerId(customerId)
                .orElseThrow(() -> new UserNotfoundException("User not found :" + customerId));

        if (customer.isEnabled()) {
            throw new UserNotfoundException("User already verified");

        }
        if (!otp.equals(customer.getOtp())) {
            throw new UserNotfoundException("otp is not equals");

        }
        customer.setEnabled(true);
        securityServiceClient.updateAccountStatus(customerId, otp).getBody();
        customerRepository.save(customer);

    }
}

