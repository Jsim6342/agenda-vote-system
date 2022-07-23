package com.agenda.vote.agenda.exception;

public class InsufficientRightCountException extends RuntimeException{
    public InsufficientRightCountException() {
        super();
    }

    public InsufficientRightCountException(String message) {
        super(message);
    }
}
