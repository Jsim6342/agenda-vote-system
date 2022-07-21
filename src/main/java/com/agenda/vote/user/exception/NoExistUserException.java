package com.agenda.vote.user.exception;

public class NoExistUserException extends RuntimeException{

    public NoExistUserException() {
        super();
    }

    public NoExistUserException(String message) {
        super(message);
    }
}
