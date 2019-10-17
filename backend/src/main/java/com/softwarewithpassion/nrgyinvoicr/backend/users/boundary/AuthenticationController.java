package com.softwarewithpassion.nrgyinvoicr.backend.users.boundary;

import com.softwarewithpassion.nrgyinvoicr.backend.users.control.TokenRefresher;
import com.softwarewithpassion.nrgyinvoicr.backend.users.control.UnauthorizedException;
import com.softwarewithpassion.nrgyinvoicr.backend.users.control.UserAuthenticator;
import com.softwarewithpassion.nrgyinvoicr.backend.users.entity.AuthenticationRequest;
import com.softwarewithpassion.nrgyinvoicr.backend.users.entity.AuthenticationResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@RestController
@RequestMapping("/api/users")
public class AuthenticationController {
    private final UserAuthenticator userAuthenticator;
    private final TokenRefresher tokenRefresher;

    public AuthenticationController(UserAuthenticator userAuthenticator, TokenRefresher tokenRefresher) {
        this.userAuthenticator = userAuthenticator;
        this.tokenRefresher = tokenRefresher;
    }

    @PostMapping(path = "/authentication")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest authenticationRequest) throws UnauthorizedException {
        return ResponseEntity.ok(userAuthenticator.authenticate(authenticationRequest));
    }

    @PostMapping(path = "/token")
    public ResponseEntity<AuthenticationResponse> refresh() throws UnauthorizedException {
        return ResponseEntity.ok(tokenRefresher.refreshToken());
    }

    @ExceptionHandler({UnauthorizedException.class})
    protected ResponseEntity<Void> handleException(UnauthorizedException e) {
        return ResponseEntity.status(UNAUTHORIZED).build();
    }
}
