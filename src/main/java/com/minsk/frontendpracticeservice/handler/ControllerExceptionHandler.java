package com.minsk.frontendpracticeservice.handler;

import com.minsk.frontendpracticeservice.domain.response.SimpleMessage;
import com.minsk.frontendpracticeservice.exception.PuppetBusinessException;
import com.minsk.frontendpracticeservice.exception.auth.AuthException;
import com.minsk.frontendpracticeservice.exception.auth.JwtException;
import com.minsk.frontendpracticeservice.exception.business.NotAllowedToCreateRequisitesException;
import com.minsk.frontendpracticeservice.exception.business.NotAllowedToSaveClientAccountException;
import com.minsk.frontendpracticeservice.exception.business.RequisitesNotFoundException;
import com.minsk.frontendpracticeservice.exception.business.UserNotFoundException;
import com.minsk.frontendpracticeservice.exception.server.ServerErrorException;
import com.minsk.frontendpracticeservice.exception.server.ServiceUnavailableException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.InvalidMediaTypeException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ControllerExceptionHandler {

    private ResponseEntity<SimpleMessage> handle(Exception ex, HttpStatus httpStatus) {

        if (httpStatus.is5xxServerError()) {
            log.error("код ошибки: {} исключение: {}, сообщение: {}",
                    httpStatus.value(),
                    ex.getClass().getName(),
                    ex.getMessage()
            );
        } else {
            log.warn("код ошибки: {} исключение: {}, сообщение: {}",
                    httpStatus.value(),
                    ex.getClass().getName(),
                    ex.getMessage()
            );
        }
        return new ResponseEntity<>(new SimpleMessage(ex.getMessage()), httpStatus);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    protected ResponseEntity<SimpleMessage> handleMethodArgumentNotValid(IllegalArgumentException exception) {
        return handle(exception, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidMediaTypeException.class)
    protected ResponseEntity<SimpleMessage> handleMethodArgumentNotValid(InvalidMediaTypeException exception) {
        return handle(exception, HttpStatus.UNSUPPORTED_MEDIA_TYPE);
    }

    @ExceptionHandler(value = {UserNotFoundException.class, RequisitesNotFoundException.class})
    protected ResponseEntity<SimpleMessage> handleBusinessEntityNotFoundException(PuppetBusinessException exception) {
        return handle(exception, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AuthException.class)
    protected ResponseEntity<SimpleMessage> handleAuthException(AuthException exception) {
        return handle(exception, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(JwtException.class)
    protected ResponseEntity<SimpleMessage> handleJwtException(JwtException exception) {
        return handle(exception, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {NotAllowedToCreateRequisitesException.class, NotAllowedToSaveClientAccountException.class})
    protected ResponseEntity<SimpleMessage> handleNotAllowedToCreateRequisites(PuppetBusinessException exception) {
        return handle(exception, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ServerErrorException.class)
    protected ResponseEntity<SimpleMessage> handleServerErrorException(ServerErrorException exception) {
        return handle(exception, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ServiceUnavailableException.class)
    protected ResponseEntity<SimpleMessage> handleServiceUnavailableException(ServiceUnavailableException exception) {
        return handle(exception, HttpStatus.SERVICE_UNAVAILABLE);
    }

}
