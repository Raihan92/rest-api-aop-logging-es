package com.progcreek.app.logging;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class RestControllerLoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(RestControllerLoggingAspect.class);

    // Pointcut for all methods in classes annotated with @RestController
    @Pointcut("within(@org.springframework.web.bind.annotation.RestController *)")
    public void restControllerMethods() {}

    // Log method details before execution
    @Before("restControllerMethods()")
    public void logBeforeMethodExecution(JoinPoint joinPoint) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        String methodName = methodSignature.getMethod().getName();
        Object[] args = joinPoint.getArgs();
        logger.info("Executing method: {} with arguments: {}", methodName, args != null ? args : "No arguments");
    }

    // Log response after method execution
    @AfterReturning(pointcut = "restControllerMethods()", returning = "response")
    public void logAfterReturning(JoinPoint joinPoint, Object response) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        String methodName = methodSignature.getMethod().getName();
        logger.info("Method: {} executed successfully. Response: {}", methodName, response != null ? response : "No response");
    }
}