package com.example.pricingservice.service;

import com.example.pricingservice.exception.PriceNotFoundException;
import com.example.pricingservice.exception.RoomException;
import com.example.pricingservice.model.dto.request.PriceRequestDto;
import com.example.pricingservice.model.dto.response.PriceResponseDto;
import com.example.pricingservice.model.dto.response.RoomResponseDto;
import com.example.pricingservice.model.entity.Price;
import com.example.pricingservice.model.mapper.PriceMapper;
import com.example.pricingservice.repository.PriceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PriceService {
    private final PriceRepository priceRepository;
    private final PriceMapper priceMapper;
    private final RoomServiceClient roomServiceClient;

    public PriceRequestDto createPrice(PriceRequestDto requestDto, Long roomId) throws RoomException {
        if (roomId == 0) {
            throw new RoomException("roomId can not be Zero");
        }

        if (requestDto.getBasePrice().compareTo(BigDecimal.ZERO) == 0) {
            throw new PriceNotFoundException("basePrice can not be zero");
        }
        if (priceRepository.existsByRoomId(roomId)) {
            throw new PriceNotFoundException("already a price this roomId");
        }
        RoomResponseDto roomDto = roomServiceClient.getRoomById(roomId).getBody();
        if (roomDto != null) {
            Price priceEntity = priceMapper.mapToEntity(requestDto, roomId);
            priceRepository.save(priceEntity);
            return requestDto;
        }
        return null;
    }

    public PriceResponseDto getPriceByRoomId(Long roomId) {
        Price priceEntity = priceRepository.findByRoomId(roomId)
                .orElseThrow(() -> new PriceNotFoundException("can not found a price with this roomId"));
        return priceMapper.mapToDto(priceEntity);
    }

    public List<PriceResponseDto> getAllPrice() {
        List<Price> priceList = priceRepository.findAll();
        if (priceList.isEmpty()) {
            return Collections.emptyList();
        }
        return priceList.stream().map(priceMapper::mapToDto).toList();
    }

    public void deletePriceById(Long id) {
        Price price = priceRepository.findById(id)
                .orElseThrow(() -> new PriceNotFoundException("can not any price with id"));
        priceRepository.delete(price);
    }

    public PriceRequestDto updatePriceByRoomId(Long roomId, PriceRequestDto requestDto) throws RoomException {
        if (roomId == 0) {
            throw new RoomException("roomId can not be Zero");
        }
        if (requestDto.getBasePrice().compareTo(BigDecimal.ZERO) == 0) {
            throw new PriceNotFoundException("basePrice can not be zero");
        }
        Price oldPrice = priceRepository.findByRoomId(roomId)
                .orElseThrow(() -> new PriceNotFoundException("can not found a price or room with this roomId"));
        if (oldPrice != null) {
            Price updatePrice = priceMapper.mapToUpdateEntity(requestDto, roomId, oldPrice);
            updatePrice.setId(oldPrice.getId());
            priceRepository.save(updatePrice);

        }
        return requestDto;
    }
}
