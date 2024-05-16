package com.example.roomservice.service;

import com.example.roomservice.exception.RoomException;
import com.example.roomservice.model.dto.request.RoomRequestDto;
import com.example.roomservice.model.dto.response.RoomResponseDto;
import com.example.roomservice.model.entity.Room;
import com.example.roomservice.model.mapper.RoomMapper;
import com.example.roomservice.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RoomService {
    private final RoomRepository roomRepository;
    private final RoomMapper roomMapper;

    public RoomRequestDto createRoom(RoomRequestDto requestDto) throws RoomException {

        if (requestDto.getCapacity() == 0) {
            throw new RoomException("room capacity not be Zero");
        }
        if (roomRepository.existsByRoomNumber(requestDto.getRoomNumber())) {
            throw new RoomException("this roomNumber already exist");
        }
        Room roomEntity = roomMapper.mapToEntity(requestDto);
        roomRepository.save(roomEntity);
        return requestDto;
    }

    public RoomResponseDto getRoomById(Long id) throws RoomException {
        Room roomEntity = roomRepository.findById(id)
                .orElseThrow(() -> new RoomException("can not found Room with this RoomId"));
        return roomMapper.mapToDto(roomEntity);

    }

    public RoomResponseDto getByRoomNumber(String roomNumber) throws RoomException {
        Room room = roomRepository.findByRoomNumber(roomNumber)
                .orElseThrow(() -> new RoomException("not found Room with this RoomNumber"));
        return roomMapper.mapToDto(room);

    }

    public List<RoomResponseDto> getAllRoom() {
        List<Room> roomList = roomRepository.findAll();
        if (roomList.isEmpty()) {
            return Collections.emptyList();
        }
        return roomList.stream().map(roomMapper::mapToDto).toList();
    }

    public void updateRoom(Long id, RoomRequestDto requestDto) throws RoomException {

        if (requestDto.getCapacity() == 0) {
            throw new RoomException("room capacity not be Zero");
        }
        if (roomRepository.existsByRoomNumber(requestDto.getRoomNumber())) {
            throw new RoomException("this roomNumber already exist");
        }
        Room oldRoom = roomRepository.findById(id)
                .orElseThrow(() -> new RoomException("can not found Room with this RoomId"));
        if (oldRoom != null) {
            Room updateRoom = roomMapper.mapToUpdateEntity(requestDto,oldRoom);
            updateRoom.setId(oldRoom.getId());
            roomRepository.save(updateRoom);
        }
    }
}
