package com.example.paymentservice.repository;

import com.example.paymentservice.model.entity.Balance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BalanceRepository extends JpaRepository<Balance, Long> {
    List<Balance> findByCustomerIdOrderByCreationDateDesc(Long customerId);
}
