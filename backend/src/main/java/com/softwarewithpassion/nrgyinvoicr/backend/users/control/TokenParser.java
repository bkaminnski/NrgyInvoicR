package com.softwarewithpassion.nrgyinvoicr.backend.users.control;

import com.softwarewithpassion.nrgyinvoicr.backend.users.entity.AuthenticatedUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;

@Component
public class TokenParser {
    private static final String NAME_CLAIM_KEY = "name";
    private static final String EMAIL_CLAIM_KEY = "email";

    public AuthenticatedUser parse(String authenticationToken, SecretKey secretKey) {
        try {
            return tryParsing(authenticationToken, secretKey);
        } catch (Exception e) {
            return null;
        }
    }

    private AuthenticatedUser tryParsing(String authenticationToken, SecretKey secretKey) {
        Jws<Claims> claims = Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(authenticationToken);

        if (!SignatureAlgorithm.HS512.getValue().equals(claims.getHeader().getAlgorithm())) {
            return null;
        }
        if (!subjectIsSpecifiedIn(claims) || !nameIsSpecifiedIn(claims) || !emailIsSpecifiedIn(claims)) {
            return null;
        }
        return new AuthenticatedUser(
                claims.getBody().getSubject(),
                claims.getBody().get(NAME_CLAIM_KEY, String.class),
                claims.getBody().get(EMAIL_CLAIM_KEY, String.class)
        );
    }

    private boolean subjectIsSpecifiedIn(Jws<Claims> claims) {
        return claims.getBody().getSubject() != null && !claims.getBody().getSubject().trim().isEmpty();
    }

    private boolean nameIsSpecifiedIn(Jws<Claims> claims) {
        return claims.getBody().get(NAME_CLAIM_KEY, String.class) != null && !claims.getBody().get(NAME_CLAIM_KEY, String.class).trim().isEmpty();
    }

    private boolean emailIsSpecifiedIn(Jws<Claims> claims) {
        return claims.getBody().get(EMAIL_CLAIM_KEY, String.class) != null && !claims.getBody().get(EMAIL_CLAIM_KEY, String.class).trim().isEmpty();
    }
}
