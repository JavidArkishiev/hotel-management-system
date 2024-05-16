package com.example.staffmanagementservice.model.dto;

import com.example.staffmanagementservice.enums.StaffRole;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class StaffResponse {
    private String firstName;

    private String lastName;

    private StaffRole staffRole;

    private String staffNumber;
    private LocalDateTime creationDate;

}
