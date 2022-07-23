package com.agenda.vote.agenda.exception;

public class AlreadyStartVoteException extends RuntimeException{
    public AlreadyStartVoteException() {
        super();
    }

    public AlreadyStartVoteException(String message) {
        super(message);
    }
}
