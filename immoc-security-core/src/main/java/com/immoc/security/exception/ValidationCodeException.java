package com.immoc.security.exception;


import org.springframework.security.core.AuthenticationException;

public class ValidationCodeException extends AuthenticationException {
    public ValidationCodeException(String msg) {
        super(msg);
    }
}
