package com.example.roomservice.model.dto.request;

import com.example.roomservice.model.RoomType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RoomRequestDto {
    @NotBlank(message = "roomNumber can not be null")
    String roomNumber;

    @NotNull(message = "roomType can not be null")
    RoomType roomType;

    int capacity;
    boolean smokingAllowed;

}
