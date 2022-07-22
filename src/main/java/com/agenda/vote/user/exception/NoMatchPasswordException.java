package com.agenda.vote.user.exception;

public class NoMatchPasswordException extends RuntimeException {

    public NoMatchPasswordException() {
        super();
    }

    public NoMatchPasswordException(String message) {
        super(message);
    }

}
