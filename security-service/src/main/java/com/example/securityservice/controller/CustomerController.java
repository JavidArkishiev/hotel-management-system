package com.example.securityservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1")
@RequiredArgsConstructor
public class CustomerController {
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("admin")
    public ResponseEntity<String> sayHello() {
        return ResponseEntity.ok("Hi i am admin");
    }

    @PreAuthorize("hasAnyAuthority('USER','ADMIN')")
    @GetMapping("user")
    public ResponseEntity<String> sayHello1() {
        return ResponseEntity.ok("Hi i am user");
    }
}
