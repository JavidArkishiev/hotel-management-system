package com.example.reservationservice.repository;

import com.example.reservationservice.model.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findByCustomerId(Long customerId);

    boolean existsByRoomIdAndCheckInDateBeforeAndCheckoutDateAfter(Long roomId, LocalDate checkInDate, LocalDate checkoutDate);

}
