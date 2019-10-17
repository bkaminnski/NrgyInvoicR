package com.softwarewithpassion.nrgyinvoicr.backend.users.control;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Optional;

@Component
public class SecretKeyProvider {
    private final SecretKey secretKey;

    public SecretKeyProvider() {
        secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS512);
    }

    Optional<SecretKey> provide() {
        return Optional.of(secretKey);
    }
}
