package com.hclc.nrgyinvoicr.backend.users.entity;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;

import static java.util.Collections.singletonList;

public class AuthenticatedUser implements Authentication {
    private final String id;
    private final String name;
    private final String email;

    public AuthenticatedUser(String id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public AuthenticatedUser(User user) {
        this.id = user.getExternalId();
        this.name = user.getFirstName() + " " + user.getLastName();
        this.email = user.getEmail();
    }

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return singletonList(new SimpleGrantedAuthority("USER"));
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getDetails() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return id;
    }

    @Override
    public boolean isAuthenticated() {
        return true;
    }

    @Override
    public void setAuthenticated(boolean authenticated) throws IllegalArgumentException {
        if (authenticated) {
            throw new IllegalArgumentException();
        }
        setAuthenticated(false);
    }
}
