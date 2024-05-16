package com.example.roomservice.controller;

import com.example.roomservice.exception.RoomException;
import com.example.roomservice.model.dto.request.RoomRequestDto;
import com.example.roomservice.model.dto.response.RoomResponseDto;
import com.example.roomservice.service.RoomService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("room")
@RequiredArgsConstructor
public class RoomController {
    private final RoomService roomService;

    @PostMapping
    public ResponseEntity<RoomRequestDto> createRoom(@RequestBody @Valid RoomRequestDto requestDto) throws RoomException {
        return new ResponseEntity<>(roomService.createRoom(requestDto), HttpStatus.CREATED);

    }

    @GetMapping("{id}")
    public ResponseEntity<RoomResponseDto> getRoomById(@PathVariable Long id) throws RoomException {
        return new ResponseEntity<>(roomService.getRoomById(id), HttpStatus.OK);

    }

    @GetMapping("get-room/{roomNumber}")
    public ResponseEntity<RoomResponseDto> getByRoomNumber(@RequestParam String roomNumber) throws RoomException {
        return new ResponseEntity<>(roomService.getByRoomNumber(roomNumber), HttpStatus.OK);

    }

    @GetMapping
    public ResponseEntity<List<RoomResponseDto>> getAllRoom() {
        return new ResponseEntity<>(roomService.getAllRoom(), HttpStatus.OK);
    }

    @PutMapping("update-room/{id}")
    public ResponseEntity<String> updateRoom(@PathVariable Long id, @RequestBody @Valid RoomRequestDto requestDto) throws RoomException {
        roomService.updateRoom(id, requestDto);
        return ResponseEntity.ok("Success");
    }
}
