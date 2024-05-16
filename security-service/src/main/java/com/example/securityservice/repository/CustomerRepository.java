package com.example.securityservice.repository;

import com.example.securityservice.entity.Customer;
import com.example.securityservice.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findByEmail(String email);

    Customer findByRole(Role role);

    boolean existsByEmail(String email);

    Optional<Customer> findByCustomerId(Long customerId);

}
