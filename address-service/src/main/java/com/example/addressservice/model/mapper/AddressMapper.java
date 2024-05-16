package com.example.addressservice.model.mapper;

import com.example.addressservice.model.dto.request.AddressRequestDto;
import com.example.addressservice.model.dto.response.AddressResponseDto;
import com.example.addressservice.model.entity.Address;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface AddressMapper {
    @Mapping(target = "creationDate", expression = "java(java.time.LocalDateTime.now())")
    Address mapToAddressEntity(AddressRequestDto requestDto, Long customerId);

    AddressResponseDto mapToAddressResponseDto(Address addressEntity);
}
