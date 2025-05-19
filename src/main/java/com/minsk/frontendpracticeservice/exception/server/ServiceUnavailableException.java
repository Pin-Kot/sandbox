package com.minsk.frontendpracticeservice.exception.server;

import com.minsk.frontendpracticeservice.exception.PuppetServerErrorException;

public class ServiceUnavailableException extends PuppetServerErrorException {

    public ServiceUnavailableException(String message) {
        super(message);
    }

}
