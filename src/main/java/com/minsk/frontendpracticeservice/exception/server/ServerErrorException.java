package com.minsk.frontendpracticeservice.exception.server;

import com.minsk.frontendpracticeservice.exception.PuppetServerErrorException;

public class ServerErrorException extends PuppetServerErrorException {

    public ServerErrorException(String message) {
        super(message);
    }

}
