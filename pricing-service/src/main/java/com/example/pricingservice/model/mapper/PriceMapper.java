package com.example.pricingservice.model.mapper;

import com.example.pricingservice.model.dto.request.PriceRequestDto;
import com.example.pricingservice.model.dto.response.PriceResponseDto;
import com.example.pricingservice.model.entity.Price;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface PriceMapper {
    @Mapping(target = "creationDate", expression = "java(java.time.LocalDateTime.now())")
    Price mapToEntity(PriceRequestDto requestDto, Long roomId);

    PriceResponseDto mapToDto(Price priceEntity);


    @Mapping(target = "creationDate", ignore = true)
    Price mapToUpdateEntity(PriceRequestDto requestDto, Long roomId, @MappingTarget Price oldPrice);
}
