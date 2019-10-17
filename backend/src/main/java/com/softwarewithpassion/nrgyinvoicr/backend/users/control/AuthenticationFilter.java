package com.softwarewithpassion.nrgyinvoicr.backend.users.control;

import com.softwarewithpassion.nrgyinvoicr.backend.users.entity.AuthenticatedUser;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@Component
public class AuthenticationFilter extends OncePerRequestFilter {
    private static final String HEADER_NAME = "Authorization";
    private static final String HEADER_PREFIX = "Bearer ";
    private final SecretKeyProvider secretKeyProvider;
    private final TokenParser tokenParser;

    public AuthenticationFilter(SecretKeyProvider secretKeyProvider, TokenParser tokenParser) {
        this.secretKeyProvider = secretKeyProvider;
        this.tokenParser = tokenParser;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        Optional<SecretKey> secretKey = secretKeyProvider.provide();
        if (secretKey.isEmpty()) {
            filterChain.doFilter(request, response);
            return;
        }
        String authorizationHeader = request.getHeader(HEADER_NAME);
        if (authorizationHeader == null || !authorizationHeader.startsWith(HEADER_PREFIX)) {
            filterChain.doFilter(request, response);
            return;
        }
        String authenticationToken = authorizationHeader.substring(HEADER_PREFIX.length());
        AuthenticatedUser authenticatedUser = tokenParser.parse(authenticationToken, secretKey.get());
        if (authenticatedUser == null) {
            filterChain.doFilter(request, response);
            return;
        }
        SecurityContextHolder.getContext().setAuthentication(authenticatedUser);
        filterChain.doFilter(request, response);
    }
}
