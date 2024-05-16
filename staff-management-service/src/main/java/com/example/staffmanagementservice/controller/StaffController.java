package com.example.staffmanagementservice.controller;

import com.example.staffmanagementservice.exception.StaffException;
import com.example.staffmanagementservice.model.dto.StaffRequestDto;
import com.example.staffmanagementservice.model.dto.StaffResponse;
import com.example.staffmanagementservice.service.StaffService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("staff")
public class StaffController {
    private final StaffService staffService;

    @PostMapping
    public ResponseEntity<String> createStaff(@Valid @RequestBody StaffRequestDto requestDto) throws StaffException {
        staffService.createStaff(requestDto);
        return ResponseEntity.ok("Success");
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public StaffResponse getStaffById(@PathVariable Long id) throws StaffException {
        return staffService.getStaffById(id);
    }

    @GetMapping("staff-number")
    public ResponseEntity<StaffResponse> getStaffByStaffNumber(String staffNumber) throws StaffException {
        return new ResponseEntity<>(staffService.getStaffByStaffNumber(staffNumber), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<StaffResponse>> getAllStaff() {
        return new ResponseEntity<>(staffService.getAllStaff(), HttpStatus.OK);
    }

    @DeleteMapping()
    public ResponseEntity<String> deleteStaffById(@RequestParam Long staffId) throws StaffException {
        staffService.deleteStaffById(staffId);
        return ResponseEntity.ok("Success");
    }

    @PutMapping
    public ResponseEntity<StaffRequestDto> updateStaffById(@RequestParam Long staffId,
                                                         @RequestBody StaffRequestDto requestDto) throws StaffException {
        return new ResponseEntity<>(staffService.updateStaffById(staffId, requestDto), HttpStatus.OK);
    }
}
