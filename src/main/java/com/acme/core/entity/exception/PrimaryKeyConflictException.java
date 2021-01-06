package com.acme.core.entity.exception;

public class PrimaryKeyConflictException extends RuntimeException {
    public PrimaryKeyConflictException(String message){
        super(message);
    }
}
