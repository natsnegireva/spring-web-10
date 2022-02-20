package com.geekbrains.spring.web.api.exceptions;

public class ServerDontWorkingException extends RuntimeException {
    public ServerDontWorkingException(String message) {
        super(message);
    }
}
