package com.minsk.frontendpracticeservice.exception.auth;

import com.minsk.frontendpracticeservice.exception.PuppetAuthException;

public class JwtException extends PuppetAuthException {

    public JwtException(String message) {
        super(message);
    }

}
