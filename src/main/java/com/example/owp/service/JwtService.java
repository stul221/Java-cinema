package com.example.owp.service;


import com.example.owp.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtService {
    private static final Logger log = LoggerFactory.getLogger(JwtService.class);
    private final String key;

    public JwtService(@Value("${jwt.secret}") String secret) {
        this.key = secret;
    }

    public String generateToken(User user) {
        return Jwts.builder()
                .subject(user.getUserName())
                .claim("userId", user.getId())
                .claim("role",user.getRole().name())
                .expiration(new Date(System.currentTimeMillis() + 86400000))
                .signWith(getSingInKey())
                .compact();
    }

    public Claims parseJwt(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(getSingInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return claims;
    }

    public boolean validateJwt(String token) {
        try {
            Jwts.parser()
                    .verifyWith(getSingInKey())
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            log.warn("Invalid JWT token", e);
            return false;
        }
    }

    private SecretKey getSingInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(key);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}