package com.agenda.vote.agenda.exception;

public class NotAllowDateException extends RuntimeException{
    public NotAllowDateException() {
        super();
    }

    public NotAllowDateException(String message) {
        super(message);
    }
}
