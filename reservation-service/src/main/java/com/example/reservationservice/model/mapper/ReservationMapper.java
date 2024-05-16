package com.example.reservationservice.model.mapper;

import com.example.reservationservice.model.dto.request.ReservationRequestDto;
import com.example.reservationservice.model.dto.response.ReservationResponseDto;
import com.example.reservationservice.model.entity.Reservation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper(componentModel = "spring")
public interface ReservationMapper {
    @Mapping(target = "paymentStatus", constant = "false")
    @Mapping(target = "active", constant = "false")
    Reservation mapToEntity(ReservationRequestDto requestDto, Long roomId, Long customerId);

    List<ReservationResponseDto> mapToDtoList(List<Reservation> reservationList);

    ReservationResponseDto mapToDto(Reservation reservation);
}
