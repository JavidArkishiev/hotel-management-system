package com.example.apigateway.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;

@Service
public class JWTService {
    private static final String secret = "413F4428472B4B6250655368566D5970337336763979244226452948404D6351";

    public void validate(String jwt) {
        Jwts.parserBuilder().setSigningKey(getSignKey()).build().parseClaimsJws(jwt);
    }

    private Key getSignKey() {
        byte[] key = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(key);
    }


}
