package com.example.securityservice.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class JwtAuthenticationResponse {
    private String accessToken;
    private String refreshToken;

}
