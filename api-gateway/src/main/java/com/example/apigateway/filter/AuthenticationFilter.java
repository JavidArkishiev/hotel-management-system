package com.example.apigateway.filter;

import com.example.apigateway.exception.InvalidTokenException;
import com.example.apigateway.exception.UnauthorizedException;
import com.example.apigateway.util.JWTService;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
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
            ServerHttpRequest request = exchange.getRequest();
            if (validator.isSecured.test(exchange.getRequest())) {
                if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                    throw new RuntimeException(new InvalidTokenException("Missing or invalid authorization header"));
                }
                String autHeaders = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
                if (autHeaders != null && autHeaders.startsWith("Bearer ")) {
                    autHeaders = autHeaders.substring(7);
                }
                try {
                    jwtService.validate(autHeaders);
                    String role = jwtService.extractRole(autHeaders);
                    if (validator.isSecuredAdmin.test(request) && !"ADMIN".equals(role)) {
                        throw new UnauthorizedException("Unauthorized access: Admin role required");
                    }
                } catch (Exception e) {
                    throw new RuntimeException(new UnauthorizedException("Unauthorized access"));
                } catch (UnauthorizedException e) {
                    throw new RuntimeException(e);
                }
            }
            return chain.filter(exchange.mutate().request(request).build());
        };
    }

}
