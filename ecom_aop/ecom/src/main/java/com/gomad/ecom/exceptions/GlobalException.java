package com.gomad.ecom.exceptions;

import com.gomad.ecom.payload.CustomApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalException {

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<CustomApiResponse> apiException(ApiException ex){
        return new ResponseEntity<>(new CustomApiResponse(ex.getMessage(), false, HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RegistrationValidationException.class)
    public ResponseEntity<Map<String, Object>> registrationValidationException(
            RegistrationValidationException ex) {

        Map<String, Object> response = new HashMap<>();

        response.put("message", "Registration validation failed");
        response.put("success", false);
        response.put("errors", ex.getErrors());

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
