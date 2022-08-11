package com.agenda.vote.user.exception;

public class AlreadyEmailUsedException extends RuntimeException{
    public AlreadyEmailUsedException() {
        super();
    }

    public AlreadyEmailUsedException(String message) {
        super(message);
    }
}
