package com.minsk.frontendpracticeservice.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@RequiredArgsConstructor
public enum Role implements GrantedAuthority {

    ADMIN("ADMIN"),
    USER("USER");

    private final String vale;

    @Override
    public String getAuthority() {
        return vale;
    }

}