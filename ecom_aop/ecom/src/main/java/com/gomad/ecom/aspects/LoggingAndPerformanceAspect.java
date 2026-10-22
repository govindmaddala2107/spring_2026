package com.gomad.ecom.aspects;

import com.gomad.ecom.payload.LoginRequestDTO;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
@Slf4j
public class LoggingAndPerformanceAspect {

//    @Around("execution(* com.gomad.ecom.controllers..*.*(..)) || execution(* com.gomad.ecom.services..*.*(..))")
    @Around("@annotation(com.gomad.ecom.aspects.LogAspect)")
    public Object logAndMeasureExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {

        long startTime = System.currentTimeMillis();
        String methodName = joinPoint.getSignature().toShortString();

        Object[] methodArgs = Arrays.stream(joinPoint.getArgs())
                .map(this::maskSensitiveData)
                .toArray();

        log.info("Entering method: {}", methodName);
        log.info("Arguments: {}", Arrays.toString(methodArgs));

        Object result = joinPoint.proceed();

        log.info("Completed {} in {} s",
                methodName,
                (System.currentTimeMillis() - startTime) / 1000.0);

        return result;
    }

    private Object maskSensitiveData(Object arg) {

        if (arg instanceof LoginRequestDTO loginRequest) {
            return String.format(
                    "LoginRequestDTO(username=%s, password=****)",
                    loginRequest.getUsername()
            );
        }

        return arg;
    }
}

















