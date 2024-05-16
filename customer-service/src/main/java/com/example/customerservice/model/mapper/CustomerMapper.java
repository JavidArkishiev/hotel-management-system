package com.example.customerservice.model.mapper;

import com.example.customerservice.model.dto.request.CustomerRequestDto;
import com.example.customerservice.model.dto.request.SignUpEmailRequest;
import com.example.customerservice.model.dto.response.CustomerResponseDto;
import com.example.customerservice.model.entity.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
@Mapper(componentModel = "spring")
public abstract class CustomerMapper {
    @Autowired
    protected PasswordEncoder passwordEncoder;


    @Mapping(target = "otp", expression = "java(generateRandomOtp())")
    @Mapping(target = "enabled", constant = "false")
    @Mapping(target = "password", expression = "java(passwordEncoder.encode(customerRequestDto.getPassword()))")
    public abstract Customer mapToEntity(CustomerRequestDto customerRequestDto);

    public String generateRandomOtp() {
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000);
        return String.valueOf(otp);
    }

    public abstract CustomerResponseDto mapToDto(Customer customerEntity);

    public abstract SignUpEmailRequest mapToSignUpEmailRequest(Customer customerEntity);

    @Mapping(target = "otp", ignore = true)
    @Mapping(target = "enabled", ignore = true)
    @Mapping(target = "password", expression = "java(passwordEncoder.encode(dto.getPassword()))")
    public abstract Customer mapToUpdateEntity(CustomerRequestDto dto, @MappingTarget Customer oldCustomer);
}
