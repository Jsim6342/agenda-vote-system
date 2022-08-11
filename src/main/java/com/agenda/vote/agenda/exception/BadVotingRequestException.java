package com.agenda.vote.agenda.exception;

public class BadVotingRequestException extends RuntimeException{
    public BadVotingRequestException() {
        super();
    }

    public BadVotingRequestException(String message) {
        super(message);
    }
}
