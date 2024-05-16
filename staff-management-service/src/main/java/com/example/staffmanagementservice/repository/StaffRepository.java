package com.example.staffmanagementservice.repository;

import com.example.staffmanagementservice.model.entity.Staff;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StaffRepository extends JpaRepository<Staff, Long> {

    boolean existsByStaffNumber(String staffNumber);

    Optional<Staff> findByStaffNumber(String staffNumber);
}
