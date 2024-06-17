package com.flypass.account.exception.domain;

public class BadRequestException extends RuntimeException {

    public BadRequestException(String ex) {
        super(ex);
    }
}
