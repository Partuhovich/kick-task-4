package org.partapp.kicktask4.exception;

public class ConnectionPoolException extends RuntimeException {
    public ConnectionPoolException(String message) {
        super(message);
    }

    public ConnectionPoolException() {
    }

    public ConnectionPoolException(Throwable cause) {
        super(cause);
    }

    public ConnectionPoolException(String message, Throwable cause) {
        super(message, cause);
    }
}
