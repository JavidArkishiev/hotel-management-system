package com.example.roomservice.model.entity;

import com.example.roomservice.model.RoomType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "room")
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(name = "room_number", nullable = false)
    String roomNumber;

    @Column(name = "room_type")
    @Enumerated(EnumType.STRING)
    RoomType roomType;

    @Column(name = "available", nullable = false)
    boolean available;

    @Column(name = "capacity",nullable = false)
    int capacity;

    @Column(name = "smoking_allowed",nullable = false)
    boolean smokingAllowed;

    @Column(name = "creation_date",nullable = false)
    LocalDateTime creationDate;

}
