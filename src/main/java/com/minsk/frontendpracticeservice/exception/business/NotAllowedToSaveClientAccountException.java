package com.minsk.frontendpracticeservice.exception.business;

import com.minsk.frontendpracticeservice.exception.PuppetBusinessException;

public class NotAllowedToSaveClientAccountException extends PuppetBusinessException {

    public NotAllowedToSaveClientAccountException(String message) {
        super(message);
    }

}
