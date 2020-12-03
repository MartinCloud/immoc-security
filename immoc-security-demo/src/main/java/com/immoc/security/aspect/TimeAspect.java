package com.immoc.security.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * filter -> interceptor -> controllerAdvice -> aspect -> controller
 */
@Aspect
@Component
public class TimeAspect {
    @Around("execution(* com.immoc.security.controller.UserController.*(..))")
    public Object handleControllerMethod(ProceedingJoinPoint pjp) throws Throwable {
        System.out.println("time aspect start");
        Object[] args = pjp.getArgs();
        for(Object arg : args) {
            System.out.println("arg is: " + arg);
        }
        Long startTime = new Date().getTime();
        Object obj = pjp.proceed(); // 调用被拦截的方法，该方法返回的结果object
        Long endTime = new Date().getTime();
        System.out.printf("time aspect end: " + (endTime - startTime));
        return obj;
    }
}
