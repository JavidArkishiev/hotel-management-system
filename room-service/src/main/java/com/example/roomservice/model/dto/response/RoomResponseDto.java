package com.example.roomservice.model.dto.response;

import com.example.roomservice.model.RoomType;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RoomResponseDto {
    String roomNumber;
    RoomType roomType;
    boolean available;
    int capacity;
    boolean smokingAllowed;

}
