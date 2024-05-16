package com.example.securityservice.dto.request;

import com.example.securityservice.enums.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerRequestDto {
    @NotBlank(message = "firstname can not be null")
    private String firstName;

    @NotBlank(message = "lastname can not be null")
    private String lastName;

    @NotBlank(message = "email can not be null")
    @Pattern(regexp = "[\\w.-]+@[\\w.-]+.\\w+$")
    private String email;

    @NotBlank(message = "password can not be null")
    private String password;

    @NotNull(message = "birthDate can not be null")
    private LocalDate birthDate;

    @NotBlank(message = "phoneNumber can not be null")
    @Pattern(regexp = "[0-9]{3}+[0-9]{3}+[0-9]{2}+[0-9]{2}")
    private String phoneNumber;
    private Role role;

}
