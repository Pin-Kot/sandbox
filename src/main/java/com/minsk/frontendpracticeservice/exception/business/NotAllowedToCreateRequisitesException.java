package com.minsk.frontendpracticeservice.exception.business;

import com.minsk.frontendpracticeservice.exception.PuppetBusinessException;

public class NotAllowedToCreateRequisitesException extends PuppetBusinessException {

    public NotAllowedToCreateRequisitesException(String message) {
        super(message);
    }

}
