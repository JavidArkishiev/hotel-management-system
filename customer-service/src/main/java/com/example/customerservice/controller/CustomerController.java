package com.example.customerservice.controller;

import com.example.customerservice.exception.ExistEmailException;
import com.example.customerservice.model.dto.request.CustomerRequestDto;
import com.example.customerservice.model.dto.request.SignUpEmailRequest;
import com.example.customerservice.model.dto.response.CustomerResponseDto;
import com.example.customerservice.model.entity.Customer;
import com.example.customerservice.service.CustomerService;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("customer")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;

    @PostMapping("signUp")
    public ResponseEntity<CustomerRequestDto> signUp(
            @RequestBody @Valid CustomerRequestDto customerRequestDto) throws ExistEmailException, MessagingException {
        return new ResponseEntity<>(customerService.signUp(customerRequestDto), HttpStatus.CREATED);
    }

    @GetMapping("{customerId}")
    public ResponseEntity<CustomerResponseDto> getCustomerById(@RequestParam Long customerId) {
        return new ResponseEntity<>(customerService.getCustomerById(customerId), HttpStatus.OK);

    }

    @GetMapping()
    public ResponseEntity<List<CustomerResponseDto>> getAllCustomer() {
        return new ResponseEntity<>(customerService.getAllCustomer(), HttpStatus.OK);

    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteCustomerById(@RequestParam Long customerId) {
        customerService.deleteCustomerById(customerId);
        return ResponseEntity.ok("Success");
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerRequestDto> updateCustomer(
            @RequestParam Long customerId, @RequestBody CustomerRequestDto dto) {
        return new ResponseEntity<>(customerService.updateCustomer(customerId, dto), HttpStatus.OK);
    }

    @GetMapping("sign-up-request")
    public ResponseEntity<SignUpEmailRequest> getSignUpEmailRequestByCustomerId(@RequestParam Long customerId) {
        return new ResponseEntity<>(customerService.getSignUpEmailRequestByCustomerId(customerId), HttpStatus.OK);
    }

    @GetMapping("customer-details-request")
    public ResponseEntity<Customer> getCustomerDetails(@RequestParam String email) {
        return new ResponseEntity<>(customerService.getCustomerDetails(email), HttpStatus.OK);
    }

    @PostMapping("update-account-status")
    public ResponseEntity<String> updateAccountStatus(@RequestParam Long customerId, @RequestParam String otp) {
        customerService.updateAccountStatus(customerId, otp);
        return ResponseEntity.ok("Success.Your account has activated. You can login a website");

    }

}
