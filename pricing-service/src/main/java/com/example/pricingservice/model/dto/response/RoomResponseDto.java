package com.example.pricingservice.model.dto.response;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RoomResponseDto {
    String roomNumber;
    boolean available;
    int capacity;
    boolean smokingAllowed;

}
