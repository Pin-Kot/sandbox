package com.minsk.frontendpracticeservice.exception.business;

import com.minsk.frontendpracticeservice.exception.PuppetBusinessException;

public class AccountNotFoundException extends PuppetBusinessException {

    public AccountNotFoundException(String message) {
        super(message);
    }

}
