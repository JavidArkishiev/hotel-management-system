package com.example.staffmanagementservice.service;

import com.example.staffmanagementservice.exception.StaffException;
import com.example.staffmanagementservice.model.dto.StaffRequestDto;
import com.example.staffmanagementservice.model.dto.StaffResponse;
import com.example.staffmanagementservice.model.entity.Staff;
import com.example.staffmanagementservice.model.mapper.StaffMapper;
import com.example.staffmanagementservice.repository.StaffRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StaffService {
    private final StaffRepository staffRepository;
    private final StaffMapper staffMapper;

    public void createStaff(StaffRequestDto requestDto) throws StaffException {
        if (staffRepository.existsByStaffNumber(requestDto.getStaffNumber())) {
            throw new StaffException("this StaffNumber already exist");
        }
        Staff staffEntity = staffMapper.mapToStaffEntity(requestDto);
        staffRepository.save(staffEntity);

    }

    public StaffResponse getStaffById(Long id) throws StaffException {
        Staff staffEntity = staffRepository.findById(id)
                .orElseThrow(() -> new StaffException("Staff not found with this StaffId"));
        return staffMapper.mapToStaffResponseDto(staffEntity);
    }

    public StaffResponse getStaffByStaffNumber(String staffNumber) throws StaffException {
        Staff staffEntity = staffRepository.findByStaffNumber(staffNumber)
                .orElseThrow(() -> new StaffException("not found Staff with this StaffNumber"));
        return staffMapper.mapToStaffResponseDto(staffEntity);
    }

    public List<StaffResponse> getAllStaff() {
        List<Staff> staffList = staffRepository.findAll();
        if (staffList.isEmpty()) {
            return Collections.emptyList();
        }
        return staffList.stream()
                .map(staffMapper::mapToStaffResponseDto)
                .toList();
    }

    public void deleteStaffById(Long staffId) throws StaffException {
        Staff staffEntity = staffRepository.findById(staffId)
                .orElseThrow(() -> new StaffException("not found Staff with this StaffNumber"));
        staffRepository.delete(staffEntity);
    }

    public StaffRequestDto updateStaffById(Long staffId, StaffRequestDto requestDto) throws StaffException {
        Staff oldStaff = staffRepository.findById(staffId)
                .orElseThrow(() -> new StaffException("not found Staff with this StaffNumber"));

        if (oldStaff != null) {
            Staff newStaff = staffMapper.mapToStaffEntity(requestDto);
            newStaff.setId(oldStaff.getId());
            staffRepository.save(newStaff);
        }
        return requestDto;
    }
}
