package com.gomad.ecom.aspects;

import com.gomad.ecom.payload.CustomApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.jpa.hibernate.SpringSessionContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Aspect
@Component
@Slf4j
public class LoginSuccessAuditAspect {

    @AfterReturning(
            pointcut = "execution(* com.gomad.ecom.controllers.UserController.signIn(..))",
            returning = "response"
    )
    public void loginSuccessfulAudit(JoinPoint joinPoint, Object response){
        if(!(response instanceof ResponseEntity<?> responseEntity)){
            return;
        }
        Object body = responseEntity.getBody();
        if(!(body instanceof CustomApiResponse customApiResponse)){
            return;
        }

        if(customApiResponse.isStatus() && customApiResponse.getMessage().equalsIgnoreCase("User login successful")){
            String username = Objects.requireNonNull(SecurityContextHolder.getContext().getAuthentication()).getName();
            log.info("User {} logged in successfully.", username);
        }
    }

}
