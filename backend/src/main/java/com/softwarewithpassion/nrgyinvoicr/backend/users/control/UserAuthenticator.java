package com.softwarewithpassion.nrgyinvoicr.backend.users.control;

import com.softwarewithpassion.nrgyinvoicr.backend.users.entity.AuthenticatedUser;
import com.softwarewithpassion.nrgyinvoicr.backend.users.entity.AuthenticationRequest;
import com.softwarewithpassion.nrgyinvoicr.backend.users.entity.AuthenticationResponse;
import com.softwarewithpassion.nrgyinvoicr.backend.users.entity.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;

@Component
public class UserAuthenticator {
    private final SecretKeyProvider secretKeyProvider;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final TokenCreator tokenCreator;

    public UserAuthenticator(SecretKeyProvider secretKeyProvider, UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder, TokenCreator tokenCreator) {
        this.secretKeyProvider = secretKeyProvider;
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.tokenCreator = tokenCreator;
    }

    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest) throws UnauthorizedException {
        SecretKey secretKey = secretKeyProvider.provide().orElseThrow(UnauthorizedException::new);
        User user = userRepository.findByEmail(authenticationRequest.getEmail()).orElseThrow(UnauthorizedException::new);
        if (!user.matches(authenticationRequest.getPassword(), bCryptPasswordEncoder)) {
            throw new UnauthorizedException();
        }
        AuthenticatedUser authenticatedUser = new AuthenticatedUser(user);
        String authenticationToken = tokenCreator.createFrom(authenticatedUser, secretKey);
        return new AuthenticationResponse(authenticationToken);
    }
}
