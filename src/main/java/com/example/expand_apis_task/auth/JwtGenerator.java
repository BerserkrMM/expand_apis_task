package com.example.expand_apis_task.auth;

import com.example.expand_apis_task.exception.JwtGeneratorException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

import static com.example.expand_apis_task.config.consts.SecurityConstants.JWT_EXPIRATION;
import static com.example.expand_apis_task.config.consts.SecurityConstants.SIGNATURE_ALGORITHM;

@Component
public class JwtGenerator {
    private static final Key KEY = Keys.secretKeyFor(SIGNATURE_ALGORITHM);
    public static final Logger LOG = LoggerFactory.getLogger("JwtGeneratorLogger");

    public String generateToken(Authentication authentication) {
        String username = authentication.getName();
        Date currentDate = new Date();
        Date expireDate = new Date(currentDate.getTime() + JWT_EXPIRATION);

        String token = Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(expireDate)
                .signWith(KEY, SIGNATURE_ALGORITHM)
                .compact();
        LOG.info("New token : {}", token);
        return token;
    }

    public String getUsernameFromJWT(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(KEY)
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(KEY)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (Exception ex) {
            throw new JwtGeneratorException("JWT was expired or incorrect", ex.getCause());
        }
    }

}
