package com.revolut.account.core.commons.exception;

public class BusinessException extends RuntimeException {

    private static final long serialVersionUID = 5128442049018373367L;

    public BusinessException(String message, Object... objects) {
        super(String.format(message, objects));
    }
}
