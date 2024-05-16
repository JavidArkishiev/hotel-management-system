package com.example.customerservice.repository;

import com.example.customerservice.model.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    boolean existsByEmail(String email);

    boolean existsByPhoneNumber(String phoneNumber);

    Optional<Customer> findByEmail(String email);

    Optional<Customer> findByCustomerId(Long customerId);
}
