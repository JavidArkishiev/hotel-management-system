package com.example.securityservice.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class JwtCheckResponse {

    private String email;
}