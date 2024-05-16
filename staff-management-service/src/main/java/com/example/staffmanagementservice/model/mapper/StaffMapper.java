package com.example.staffmanagementservice.model.mapper;

import com.example.staffmanagementservice.model.dto.StaffRequestDto;
import com.example.staffmanagementservice.model.dto.StaffResponse;
import com.example.staffmanagementservice.model.entity.Staff;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface StaffMapper {
    @Mapping(target = "creationDate", expression = "java(java.time.LocalDateTime.now())")
    Staff mapToStaffEntity(StaffRequestDto requestDto);

    StaffResponse mapToStaffResponseDto(Staff staffEntity);
}
