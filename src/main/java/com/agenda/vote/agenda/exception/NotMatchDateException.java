package com.agenda.vote.agenda.exception;

public class NotMatchDateException extends RuntimeException{
    public NotMatchDateException() {
        super();
    }

    public NotMatchDateException(String message) {
        super(message);
    }
}
