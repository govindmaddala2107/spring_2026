package com.gomad.ecom.exceptions;


public class ApiException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private String message;

    public ApiException(String message){
        super(message);
    }
}
