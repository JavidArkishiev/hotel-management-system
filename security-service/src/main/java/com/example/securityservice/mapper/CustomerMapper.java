package com.example.securityservice.mapper;

import com.example.securityservice.dto.request.CustomerRequestDto;
import com.example.securityservice.entity.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public abstract class CustomerMapper {
    @Autowired
    protected PasswordEncoder passwordEncoder;

    @Mapping(target = "otp", ignore = true)
    @Mapping(target = "enabled", ignore = true)
    @Mapping(target = "password", expression = "java(passwordEncoder.encode(dto.getPassword()))")
    public abstract Customer mapToUpdateEntity(CustomerRequestDto dto, @MappingTarget Customer oldCustomer);
}
