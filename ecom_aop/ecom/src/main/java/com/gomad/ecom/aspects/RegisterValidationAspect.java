package com.gomad.ecom.aspects;

import com.gomad.ecom.exceptions.RegistrationValidationException;
import com.gomad.ecom.payload.SignupRequestDTO;
import com.gomad.ecom.repositories.UserRespository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.security.authentication.password.CompromisedPasswordChecker;
import org.springframework.security.authentication.password.CompromisedPasswordDecision;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Aspect
@Component
@Slf4j
@RequiredArgsConstructor
public class RegisterValidationAspect {

    private final CompromisedPasswordChecker compromisedPasswordChecker;
    private final UserRespository userRespository;

    @Before("execution(* com.gomad.ecom.controllers.UserController.registerUser(..))")
    public void validBeforeRegister(JoinPoint joinPoint) {

        Object[] args = joinPoint.getArgs();
        SignupRequestDTO requestDTO = (SignupRequestDTO) args[0];

        log.info("Validating user registration request");

        Map<String, String> errors = new HashMap<>();

        // Email validation
        if (userRespository.existsByEmail(requestDTO.getEmail())) {
            errors.put("email", "Email already exists");
        }

        // Username validation
        if (userRespository.existsByUsername(requestDTO.getUsername())) {
            errors.put("username", "Username already exists");
        }

        // Password validation
        try {
            CompromisedPasswordDecision decision =
                    compromisedPasswordChecker.check(requestDTO.getPassword());

            if (decision.isCompromised()) {
                errors.put(
                        "password",
                        "Password has been exposed in a data breach"
                );
            }

        } catch (Exception ex) {
            log.warn("Could not perform HIBP password check: {}", ex.getMessage());
        }

        if (!errors.isEmpty()) {
            throw new RegistrationValidationException(errors);
        }

        log.info("Validation successful");
    }
}