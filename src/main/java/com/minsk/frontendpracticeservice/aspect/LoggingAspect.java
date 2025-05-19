package com.minsk.frontendpracticeservice.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class LoggingAspect {

    @Around("within(com.aston.frontendpracticeservice.service..*)")
    public Object logMethodExecution(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();

        log.info("Вызов метода: {}", methodName);
        try {
            log.debug("Метод {} вызван с парметрами: {}", methodName, args);
            Object result = joinPoint.proceed(args);
            log.debug("Метод {} вернул: {}", methodName, result);
            return result;
        } catch (Exception e) {
            log.error("Исключение в методе {}: {}", methodName, e.getMessage(), e);
            throw e;
        } finally {
            log.info("Завершение метода: {}", methodName);
        }
    }

}
