package com.agenda.vote.agenda.exception;

public class NoExistVoteException extends RuntimeException{
    public NoExistVoteException() {
        super();
    }
    public NoExistVoteException(String message) {
        super(message);
    }
}
