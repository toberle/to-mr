package com.github.toberle.tomr.exception;

public class OutboxMessageSendException extends RuntimeException {

    public OutboxMessageSendException(Throwable cause) {
        super(cause);
    }

    public OutboxMessageSendException(String message, Throwable cause) {
        super(message, cause);
    }
}
