package com.example.addressservice.service;

import com.example.addressservice.client.CustomerServiceClient;
import com.example.addressservice.exception.AddressException;
import com.example.addressservice.model.dto.request.AddressRequestDto;
import com.example.addressservice.model.dto.response.AddressResponseDto;
import com.example.addressservice.model.dto.response.CustomerResponseDto;
import com.example.addressservice.model.entity.Address;
import com.example.addressservice.model.mapper.AddressMapper;
import com.example.addressservice.repo.AddressRepo;
import lombok.RequiredArgsConstructor;
import org.hibernate.mapping.Collection;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AddressService {
    private final AddressRepo addressRepo;
    private final AddressMapper addressMapper;
    private final CustomerServiceClient customerServiceClient;

    public void createAddress(AddressRequestDto requestDto, Long customerId) throws AddressException {

        CustomerResponseDto customerResponseDto = customerServiceClient.getCustomerById(customerId).getBody();
        if (customerResponseDto == null) {
            throw new AddressException("customer not found with this customerId");
        }

        if (addressRepo.existsByCustomerId(customerId)) {
            throw new AddressException("already have a address with this customerId");
        }
        Address addressEntity = addressMapper.mapToAddressEntity(requestDto, customerId);
        addressRepo.save(addressEntity);

    }

    public AddressResponseDto getAddressByCustomerId(Long customerId) throws AddressException {
        Address addressEntity = addressRepo.findByCustomerId(customerId)
                .orElseThrow(() -> new AddressException("address not found with this customerId"));
        return addressMapper.mapToAddressResponseDto(addressEntity);

    }

    public void updateAddressByCustomerId(Long customerId, AddressRequestDto requestDto) throws AddressException {

        Address oldAddress = addressRepo.findByCustomerId(customerId)
                .orElseThrow(() -> new AddressException("address not found with this customerId"));
        if (oldAddress != null) {
            Address newAddress = addressMapper.mapToAddressEntity(requestDto, customerId);
            newAddress.setId(oldAddress.getId());
            addressRepo.save(newAddress);
        }
    }

    public List<AddressResponseDto> getAllAddress() {
        List<Address> addressList = addressRepo.findAll();
        if (addressList.isEmpty()) {
            return Collections.EMPTY_LIST;
        }
        return addressList.stream()
                .map(addressMapper::mapToAddressResponseDto)
                .toList();
    }

    public void deleteAddressById(Long addressId) throws AddressException {
        Address address = addressRepo.findById(addressId)
                .orElseThrow(() -> new AddressException("address not found with this addressId " + addressId));
        addressRepo.delete(address);
    }
}
