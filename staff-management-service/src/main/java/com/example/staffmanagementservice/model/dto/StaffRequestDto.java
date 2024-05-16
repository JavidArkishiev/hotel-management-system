package com.example.staffmanagementservice.model.dto;

import com.example.staffmanagementservice.enums.StaffRole;
import jakarta.persistence.Column;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class StaffRequestDto {

    private String firstName;

    private String lastName;

    private StaffRole staffRole;

    private String staffNumber;

}
