package com.agenda.vote.agenda.exception;

public class NoExistAgendaException extends RuntimeException{

    public NoExistAgendaException() {
        super();
    }

    public NoExistAgendaException(String message) {
        super(message);
    }

}
