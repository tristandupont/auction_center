package com.github.tristandupont.auction_center.controller.error;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.NoSuchElementException;

@ControllerAdvice
public class ExceptionHandlingController {

    private static final Logger LOG = LoggerFactory.getLogger(ExceptionHandlingController.class);

    @ExceptionHandler(NoSuchElementException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void noSuchElementException(final Exception e) {
        LOG.warn("NoSuchElementException => {}", e.getMessage(), e);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public void illegalArgumentException(final Exception e) {
        LOG.warn("IllegalArgumentException => {}", e.getMessage(), e);
    }

    @ExceptionHandler(IllegalStateException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public void illegalStateException(final Exception e) {
        LOG.warn("IllegalStateException => {}", e.getMessage(), e);
    }

    @ExceptionHandler(value = {Exception.class, RuntimeException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public void anyException(final Exception e) {
        LOG.error("Exception => {}", e.getMessage(), e);
    }

}
