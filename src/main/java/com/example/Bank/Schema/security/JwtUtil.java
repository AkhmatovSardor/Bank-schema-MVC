package com.example.Bank.Schema.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {
    @Value(value = "${secret.key}")
    private String key;

    public String generateToken(String customer) {
        return Jwts.builder()
                .setSubject(customer)
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 3))
                .signWith(Keys.hmacShaKeyFor(key.getBytes()))
                .compact();
    }

    public Boolean isValid(String token) {
        if (!parser().isSigned(token)) return false;
        try {
            Claims body = parser()
                    .parseClaimsJws(token)
                    .getBody();
            return !StringUtils.isBlank(body.getSubject());
        } catch (Exception e) {
            return false;
        }
    }

    public <T> T getClaim(String name, String token, Class<T> type) {
        try {
            return parser()
                    .parseClaimsJws(token)
                    .getBody()
                    .get(name, type);
        } catch (Exception e) {
            return null;
        }
    }

    public JwtParser parser() {
        return Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(key.getBytes()))
                .build();
    }
}
