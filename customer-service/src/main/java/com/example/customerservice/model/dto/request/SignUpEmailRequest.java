
package com.example.customerservice.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class SignUpEmailRequest {

    @NotBlank(message = "email can not be null")
    @Pattern(regexp = "[\\w.-]+@[\\w.-]+.\\w+$")
    private String email;

    @NotBlank(message = "otp can not be null")
    private String otp;

}
