package com.immoc.security.handler;

import com.immoc.security.exception.UserNotExistException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ConsoleExceptionHandler {

    @ExceptionHandler(UserNotExistException.class)
    public Map<String, String> handleUserNotException(UserNotExistException ex) {
        Map<String, String> map = new HashMap<>();
        map.put("id", ex.getId());
        map.put("message", ex.getMessage());
        return map;
    }
}
