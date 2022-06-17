package com.webapp.services;

public class Exceptions {

    public static class NotFoundException extends RuntimeException {
        public NotFoundException(String message) {
            super(message);
        }
    }

    public static class ServiceException extends RuntimeException {
        public ServiceException(String message) {
            super(message);
        }
    }

}
