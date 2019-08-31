package com.hclc.nrgyinvoicr.backend.users.control;

import com.hclc.nrgyinvoicr.backend.users.entity.AuthenticatedUser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;

import static java.time.Instant.now;
import static java.time.temporal.ChronoUnit.MINUTES;
import static java.util.Date.from;

@Component
class TokenCreator {

    String createFrom(AuthenticatedUser authenticatedUser, SecretKey secretKey) {
        return Jwts.builder()
                .setSubject(authenticatedUser.getId())
                .claim("email", authenticatedUser.getEmail())
                .claim("name", authenticatedUser.getName())
                .setExpiration(from(now().plus(5, MINUTES)))
                .signWith(secretKey, SignatureAlgorithm.HS512)
                .compact();
    }
}
