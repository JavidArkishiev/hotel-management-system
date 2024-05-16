package com.example.roomservice.model.mapper;

import com.example.roomservice.model.dto.request.RoomRequestDto;
import com.example.roomservice.model.dto.response.RoomResponseDto;
import com.example.roomservice.model.entity.Room;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface RoomMapper {
    @Mapping(target = "creationDate", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "available", constant = "true")
    Room mapToEntity(RoomRequestDto requestDto);

    RoomResponseDto mapToDto(Room roomEntity);

    @Mapping(target = "creationDate", ignore = true)
    @Mapping(target = "available", constant = "true")
    Room mapToUpdateEntity(RoomRequestDto requestDto, @MappingTarget Room room);
}
