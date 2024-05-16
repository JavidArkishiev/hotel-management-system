package com.example.reservationservice.service;

import com.example.reservationservice.exception.CustomerNotFoundException;
import com.example.reservationservice.exception.ReservationException;
import com.example.reservationservice.model.dto.request.ReservationRequestDto;
import com.example.reservationservice.model.dto.request.ReservationUpdateRequestDto;
import com.example.reservationservice.model.dto.response.CustomerResponseDto;
import com.example.reservationservice.model.dto.response.PriceDto;
import com.example.reservationservice.model.dto.response.ReservationResponseDto;
import com.example.reservationservice.model.entity.Reservation;
import com.example.reservationservice.model.mapper.ReservationMapper;
import com.example.reservationservice.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final ReservationMapper reservationMapper;
    private final PriceServiceClient priceServiceClient;
    private final CustomerServiceClient customerServiceClient;


    public ReservationRequestDto createReservation(ReservationRequestDto reservationDto, Long roomId, Long customerId) throws ReservationException, CustomerNotFoundException {

        boolean checkDate = reservationDto.getCheckInDate().isAfter(reservationDto.getCheckoutDate());
        if (checkDate) {
            throw new ReservationException("Reservation wrong checkout date");
        }

        if (reservationRepository.existsByRoomIdAndCheckInDateBeforeAndCheckoutDateAfter(roomId, reservationDto.getCheckoutDate(), reservationDto.getCheckInDate())) {
            throw new ReservationException("This roomId is already booked for this date");
        }
        if (!Objects.requireNonNull(customerServiceClient.getCustomerById(customerId).getBody()).isEnabled()) {
            throw new CustomerNotFoundException("this customer is not active");
        }
        PriceDto priceDto = priceServiceClient.getPriceByRoomId(roomId).getBody();
        CustomerResponseDto customer = customerServiceClient.getCustomerById(customerId).getBody();
        if (customer != null) {
            Reservation reservationEntity = new Reservation();
            reservationEntity.setActive(false);
            reservationEntity.setCheckInDate(reservationDto.getCheckInDate());
            reservationEntity.setCheckoutDate(reservationDto.getCheckoutDate());
            reservationEntity.setCustomerId(customerId);
            reservationEntity.setPaymentStatus(false);
            reservationEntity.setRoomId(roomId);
            reservationEntity.setTotalPrice(calculateTotalPrice(priceDto, reservationDto));

            reservationRepository.save(reservationEntity);
            return reservationDto;

        }
        return null;
    }

    private BigDecimal calculateTotalPrice(PriceDto priceDto, ReservationRequestDto reservationDto) throws ReservationException {
        BigDecimal totalPrice = priceDto.getBasePrice()
                .add(priceDto.getAdditionalServicePrice())
                .subtract(priceDto.getDiscount());

        long reservationDuration = ChronoUnit.DAYS.between(reservationDto.getCheckInDate(), reservationDto.getCheckoutDate());
        if (reservationDuration <= 0) {
            throw new ReservationException("reservation duration may not be least one night ");
        }

        totalPrice = totalPrice.multiply(BigDecimal.valueOf(reservationDuration));

        return totalPrice;
    }


    public List<ReservationResponseDto> getAllReservations() {
        List<Reservation> reservationList = reservationRepository.findAll();
        return reservationMapper.mapToDtoList(reservationList);
    }

    public List<ReservationResponseDto> getReservationByCustomerId(Long customerId) throws CustomerNotFoundException {
        List<Reservation> reservation = reservationRepository.findByCustomerId(customerId);
        if (reservation.isEmpty()) {
            throw new CustomerNotFoundException("can not any reservation with this customerId");
        }
        return reservationMapper.mapToDtoList(reservation);
    }

    public List<ReservationResponseDto> getAnyReservationByCustomerId(Long customerId) throws CustomerNotFoundException {
        List<Reservation> reservation = reservationRepository.findByCustomerId(customerId);
        if (reservation.isEmpty()) {
            throw new CustomerNotFoundException("can not any reservation with this customerId");
        }
        List<ReservationResponseDto> reservationList = reservation.stream()
                .filter(Reservation::isActive)
                .map(reservationMapper::mapToDto)
                .toList();
        if (reservation.isEmpty()) {
            return Collections.emptyList();
        } else return reservationList;

    }

    public void deleteByCustomerId(Long customerId) throws CustomerNotFoundException, ReservationException {
        List<Reservation> reservationList = reservationRepository.findByCustomerId(customerId);
        if (reservationList.isEmpty()) {
            throw new CustomerNotFoundException("reservation not found with this customerId");
        }

        List<Reservation> inactiveReservation = reservationList.stream()
                .filter(reservation -> !reservation.isActive()).toList();
        if (inactiveReservation.size() > 0) {

            reservationRepository.deleteAll(inactiveReservation);

        } else
            throw new ReservationException("There are one or several active reservation. " +
                    "There is not any inActive reservation with customerId ");
    }

    public void deleteReservationById(Long reservationId) throws ReservationException {
        Reservation reservationEntity = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new ReservationException("can not any reservation with reservationId"));

        if (!reservationEntity.isActive()) {
            reservationRepository.delete(reservationEntity);
        } else throw new ReservationException("can not any inActive reservation with reservationId");

    }

    public void updateReservationById(Long id, Long customerId, ReservationUpdateRequestDto updateRequestDto) throws ReservationException {
        if (updateRequestDto.getRoomId() == 0) {
            throw new ReservationException("roomId can not be null");
        }


        Reservation oldReservation = reservationRepository.findById(id)
                .orElseThrow(() -> new ReservationException("can not any reservation with this reservationId "));
        boolean checkDate = updateRequestDto.getCheckInDate().isAfter(updateRequestDto.getCheckoutDate());
        if (checkDate) {
            throw new ReservationException("Reservation wrong checkout date");
        }

        if (reservationRepository.existsByRoomIdAndCheckInDateBeforeAndCheckoutDateAfter(updateRequestDto.getRoomId(), updateRequestDto.getCheckoutDate(), updateRequestDto.getCheckInDate())) {
            throw new ReservationException("This roomId is already booked for this date");
        }
        PriceDto priceDto = priceServiceClient.getPriceByRoomId(updateRequestDto.getRoomId()).getBody();

        if (oldReservation != null) {
            Reservation updateReservation = new Reservation();
            updateReservation.setId(oldReservation.getId());
            updateReservation.setRoomId(updateRequestDto.getRoomId());
            updateReservation.setCustomerId(customerId);
            updateReservation.setCheckInDate(updateRequestDto.getCheckInDate());
            updateReservation.setCheckoutDate(updateRequestDto.getCheckoutDate());
            updateReservation.setPaymentStatus(false);
            updateReservation.setActive(false);
            updateReservation.setTotalPrice(calculateUpdateTotalPrice(priceDto, updateRequestDto));
            updateReservation.setCreationDate(oldReservation.getCreationDate());
            reservationRepository.save(updateReservation);
        } else throw new ReservationException("this reservation is active");

    }

    private BigDecimal calculateUpdateTotalPrice(PriceDto priceDto, ReservationUpdateRequestDto reservationDto) throws ReservationException {
        BigDecimal totalPrice = priceDto.getBasePrice()
                .add(priceDto.getAdditionalServicePrice())
                .subtract(priceDto.getDiscount());

        long reservationDuration = ChronoUnit.DAYS.between(reservationDto.getCheckInDate(), reservationDto.getCheckoutDate());
        if (reservationDuration <= 0) {
            throw new ReservationException("reservation duration may not be least one night ");
        }

        totalPrice = totalPrice.multiply(BigDecimal.valueOf(reservationDuration));

        return totalPrice;
    }


    public List<ReservationResponseDto> getInActiveReservationByCustomerId(Long customerId) {
        List<Reservation> reservationList = reservationRepository.findByCustomerId(customerId);
        if (reservationList.isEmpty()) {
            return Collections.emptyList();
        }
        return reservationList.stream()
                .filter(reservation -> !reservation.isActive())
                .map(reservationMapper::mapToDto)
                .toList();

    }

    public ReservationResponseDto getReservationById(Long reservationId) throws ReservationException {
        Reservation reservationEntity = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new ReservationException("reservation not found with reservationId"));
        return reservationMapper.mapToDto(reservationEntity);
    }

    public void updateReservationStatus(Long reservationId) throws ReservationException {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new ReservationException("Reservation not found"));
        if (reservation.isActive() && reservation.isPaymentStatus()) {
            throw new ReservationException("you can not update reservation status." +
                    "Because this reservation is active");
        }
        reservation.setActive(true);
        reservation.setPaymentStatus(true);
        reservationRepository.save(reservation);
    }
}


