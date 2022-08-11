package com.agenda.vote.common.exception;

public class NoAuthorizedException extends RuntimeException{
    public NoAuthorizedException() {
        super();
    }

    public NoAuthorizedException(String message) {
        super(message);
    }

}
