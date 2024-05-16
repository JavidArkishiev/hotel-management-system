package com.example.staffmanagementservice.model.entity;

import com.example.staffmanagementservice.enums.StaffRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "staff")
public class Staff {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "staff_number", nullable = false, unique = true)
    private String staffNumber;


    @Column(name = "creation_date", nullable = false)
    private LocalDateTime creationDate;

    @Column(name = "staff_role", nullable = false)
    @Enumerated(EnumType.STRING)
    private StaffRole staffRole;
}
