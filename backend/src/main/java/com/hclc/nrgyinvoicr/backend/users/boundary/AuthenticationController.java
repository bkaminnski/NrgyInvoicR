package com.hclc.nrgyinvoicr.backend.users.boundary;

import com.hclc.nrgyinvoicr.backend.users.control.UnauthorizedException;
import com.hclc.nrgyinvoicr.backend.users.control.UserAuthenticator;
import com.hclc.nrgyinvoicr.backend.users.entity.AuthenticationRequest;
import com.hclc.nrgyinvoicr.backend.users.entity.AuthenticationResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@RestController
@RequestMapping("/api/users/authentication")
public class AuthenticationController {
    private final UserAuthenticator userAuthenticator;

    public AuthenticationController(UserAuthenticator userAuthenticator) {
        this.userAuthenticator = userAuthenticator;
    }

    @PostMapping
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest authenticationRequest) throws UnauthorizedException {
        return ResponseEntity.ok(userAuthenticator.authenticate(authenticationRequest));
    }

    @ExceptionHandler({UnauthorizedException.class})
    protected ResponseEntity<Void> handleException(UnauthorizedException e) {
        return ResponseEntity.status(UNAUTHORIZED).build();
    }
}
