package com.example.addressservice.repo;

import com.example.addressservice.model.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AddressRepo extends JpaRepository<Address, Long> {

    boolean existsByCustomerId(Long customerId);

    Optional<Address> findByCustomerId(Long customerId);
}
