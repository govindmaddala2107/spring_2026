package com.gomad.postgresql_tut.exceptions;

public class APIException extends RuntimeException {
    private final static long serialVersionUID = 1L;

    public APIException(String message) {
        super(message);
    }

    public APIException(String message, Throwable cause) {

    }
}
