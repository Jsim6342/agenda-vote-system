package com.agenda.vote.agenda.exception;

public class TypeInsufficientConditionException extends RuntimeException{
    public TypeInsufficientConditionException() {
        super();
    }

    public TypeInsufficientConditionException(String message) {
        super(message);
    }
}
