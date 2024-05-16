package com.example.securityservice.service.impl;

import com.example.securityservice.repository.CustomerRepository;
import com.example.securityservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final CustomerRepository customerRepository;

    public UserDetailsService userDetailsService() {
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) {
                return customerRepository.findByEmail(username)
                        .orElseThrow(() -> new UsernameNotFoundException("Customer not found"));
            }
        };

    }
}
