package com.hclc.nrgyinvoicr.backend.users.control;

import com.hclc.nrgyinvoicr.backend.users.entity.AuthenticatedUser;
import com.hclc.nrgyinvoicr.backend.users.entity.AuthenticationResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;

@Component
public class TokenRefresher {
    private final SecretKeyProvider secretKeyProvider;
    private final TokenCreator tokenCreator;

    public TokenRefresher(SecretKeyProvider secretKeyProvider, TokenCreator tokenCreator) {
        this.secretKeyProvider = secretKeyProvider;
        this.tokenCreator = tokenCreator;
    }

    public AuthenticationResponse refreshToken() throws UnauthorizedException {
        AuthenticatedUser authenticatedUser = (AuthenticatedUser) SecurityContextHolder.getContext().getAuthentication();
        SecretKey secretKey = secretKeyProvider.provide().orElseThrow(UnauthorizedException::new);
        String authenticationToken = tokenCreator.createFrom(authenticatedUser, secretKey);
        return new AuthenticationResponse(authenticationToken);
    }
}
