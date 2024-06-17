package com.flypass.account.exception.domain;

public class UnauthorizedException extends RuntimeException {
    public UnauthorizedException(String ex) {
        super(ex);
    }

}
