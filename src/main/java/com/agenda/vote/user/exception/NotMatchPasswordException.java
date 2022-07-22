package com.agenda.vote.user.exception;

public class NotMatchPasswordException extends RuntimeException {

    public NotMatchPasswordException() {
        super();
    }

    public NotMatchPasswordException(String message) {
        super(message);
    }

}
