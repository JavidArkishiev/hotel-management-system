package com.example.apigateway.filter;

import com.example.apigateway.exception.InvalidTokenException;
import com.example.apigateway.exception.UnauthorizedException;
import com.example.apigateway.util.JWTService;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory {

    private final RouteValidator validator;
    private final JWTService jwtService;

    public AuthenticationFilter(RouteValidator validator, JWTService jwtService) {
        this.validator = validator;
        this.jwtService = jwtService;
    }

    @Override
    public GatewayFilter apply(Object config) {
        return (exchange, chain) -> {
            if (validator.isSecured.test(exchange.getRequest())) {
                String authHeaders = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
                if (authHeaders == null || !authHeaders.startsWith("Bearer ")) {
                    throw new RuntimeException(new InvalidTokenException("Missing or invalid authorization header"));
                }
                String token = authHeaders.substring(7);
                try {
                    jwtService.validate(token);
                } catch (Exception e) {
                    throw new RuntimeException(new UnauthorizedException("Unauthorized access"));
                }
            }
            return chain.filter(exchange);
        };
    }

}
