package com.example.securityservice.controller;

import com.example.securityservice.dto.request.CustomerRequestDto;
import com.example.securityservice.dto.request.SignInRequest;
import com.example.securityservice.dto.request.UserJwtCheckRequest;
import com.example.securityservice.dto.response.JwtAuthenticationResponse;
import com.example.securityservice.dto.response.JwtCheckResponse;
import com.example.securityservice.entity.Customer;
import com.example.securityservice.service.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping("/saveCustomer")
    public ResponseEntity<Customer> saveCustomer(@RequestBody CustomerRequestDto signUpRequest) {
        return ResponseEntity.ok(authenticationService.saveCustomer(signUpRequest));
    }

    @PostMapping("/signIn")
    public ResponseEntity<JwtAuthenticationResponse> signIn(@RequestBody @Valid SignInRequest sign) {
        return ResponseEntity.ok(authenticationService.sigIn(sign));
    }

    @PostMapping("update-account-status")
    public ResponseEntity<String> updateAccountStatus(@RequestParam Long customerId, @RequestParam String otp) {
        authenticationService.updateAccountStatus(customerId, otp);
        return ResponseEntity.ok("Success.Your account has activated. You can login a website");

    }

    @PostMapping("check")
    public ResponseEntity<JwtCheckResponse> checkJwt(@RequestBody @Valid UserJwtCheckRequest request) {
        return ResponseEntity.ok(authenticationService.validateJwt(request));
    }

//    @PutMapping("/{id}")
//    public ResponseEntity<CustomerRequestDto> updateCustomer(
//            @RequestParam Long customerId, @RequestBody CustomerRequestDto dto) {
//        return new ResponseEntity<>(authenticationService.updateCustomer(customerId, dto), HttpStatus.OK);
//    }


}
