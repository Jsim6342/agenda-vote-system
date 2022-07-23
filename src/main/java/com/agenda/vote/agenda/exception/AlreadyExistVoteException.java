package com.agenda.vote.agenda.exception;

public class AlreadyExistVoteException extends RuntimeException{
    public AlreadyExistVoteException() {
        super();
    }

    public AlreadyExistVoteException(String message) {
        super(message);
    }
}
