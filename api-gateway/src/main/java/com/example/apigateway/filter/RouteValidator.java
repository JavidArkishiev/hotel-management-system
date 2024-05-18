package com.example.apigateway.filter;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Predicate;

@Component
public class RouteValidator {

    public static final List<String> openApiEndPoint = List.of(
            "auth/saveCustomer",
            "auth/signIn",
            "auth/update-account-status",
            "auth/check",
            "customer/signUp",
            "customer/update-account-status",
            "notifications/send-signUp-notification",
            "notifications/verify"
    );
    public static final List<String> admin = List.of(
            "customer/{id}",
            "customer",
            "address",
            "price",
            "price/{roomId}",
            "price/{id}",
            "room",
            "room/update-room/{id}"
    );

    public Predicate<ServerHttpRequest> isSecured =
            request -> openApiEndPoint
                    .stream()
                    .noneMatch(uri -> request.getURI().getPath().contains(uri));
    public Predicate<ServerHttpRequest> hasAccessAdmin =
            request -> admin
                    .stream()
                    .anyMatch(uri -> request.getURI().getPath().contains(uri));

}
