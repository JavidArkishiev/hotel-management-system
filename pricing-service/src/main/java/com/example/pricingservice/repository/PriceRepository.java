package com.example.pricingservice.repository;

import com.example.pricingservice.model.entity.Price;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PriceRepository extends JpaRepository<Price, Long> {
    Optional<Price> findByRoomId(Long roomId);

    boolean existsByRoomId(Long roomId);
}
