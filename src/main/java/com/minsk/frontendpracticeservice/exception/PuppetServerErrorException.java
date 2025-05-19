package com.minsk.frontendpracticeservice.exception;

public abstract class PuppetServerErrorException extends PuppetException {

    public PuppetServerErrorException(String message) {
        super(message);
    }

}
