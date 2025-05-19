package com.minsk.frontendpracticeservice.exception.business;

import com.minsk.frontendpracticeservice.exception.PuppetBusinessException;

public class UserNotFoundException extends PuppetBusinessException {

    public UserNotFoundException(String message) {
        super(message);
    }

}
