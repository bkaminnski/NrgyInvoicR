package com.softwarewithpassion.nrgyinvoicr.backend.users.entity;

public class AuthenticationResponse {
    private final String authenticationToken;

    public AuthenticationResponse(String authenticationToken) {
        this.authenticationToken = authenticationToken;
    }

    public String getAuthenticationToken() {
        return authenticationToken;
    }
}
