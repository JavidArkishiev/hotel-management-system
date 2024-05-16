package com.example.addressservice.controller;

import com.example.addressservice.exception.AddressException;
import com.example.addressservice.model.dto.request.AddressRequestDto;
import com.example.addressservice.model.dto.response.AddressResponseDto;
import com.example.addressservice.service.AddressService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.bouncycastle.pqc.crypto.util.PQCOtherInfoGenerator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("address")
@RestController
@RequiredArgsConstructor
public class AddressController {
    private final AddressService addressService;

    @PostMapping
    public ResponseEntity<String> createAddress(@Valid @RequestBody AddressRequestDto requestDto,
                                                @RequestParam Long customerId) throws AddressException {
        addressService.createAddress(requestDto, customerId);
        return ResponseEntity.ok("Success");
    }

    @GetMapping("/{customerId}")
    @ResponseStatus(HttpStatus.OK)
    public AddressResponseDto getAddressByCustomerId(@PathVariable Long customerId) throws AddressException {
        return addressService.getAddressByCustomerId(customerId);
    }

    @PutMapping()
    public String updateAddressByCustomerId(@RequestParam Long customerId,
                                            @Valid @RequestBody AddressRequestDto requestDto) throws AddressException {
        addressService.updateAddressByCustomerId(customerId, requestDto);
        return "Success";
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<AddressResponseDto> getAllAddress() {
        return addressService.getAllAddress();
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.OK)
    public String deleteAddressById(@RequestParam Long addressId) throws AddressException {
        addressService.deleteAddressById(addressId);
        return "Success";
    }
}
