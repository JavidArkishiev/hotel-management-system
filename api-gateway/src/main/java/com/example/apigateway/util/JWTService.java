package com.example.apigateway.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.function.Function;

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

    public String extractRole(String jwtToken) {
        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(secret).parseClaimsJws(jwtToken);

        Claims body = claimsJws.getBody();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode roleNode = objectMapper.convertValue(body.get("role"), JsonNode.class);

        if (roleNode != null && roleNode.isArray() && roleNode.size() > 0) {
            return roleNode.get(0).get("authority").asText();
        } else {
            return "USER";
        }
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolvers) {
        final Claims claims = extractAllClaims(token);
        return claimsResolvers.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }


}
