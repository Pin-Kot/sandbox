package com.minsk.frontendpracticeservice.exception.business;

import com.minsk.frontendpracticeservice.exception.PuppetBusinessException;

public class RequisitesNotFoundException extends PuppetBusinessException {

    public RequisitesNotFoundException(String message) {
        super(message);
    }

}
