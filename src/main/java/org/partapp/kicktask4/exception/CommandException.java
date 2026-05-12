package org.partapp.kicktask4.exception;

public class CommandException extends Exception {
    public CommandException(String message) {
        super(message);
    }

    public CommandException() {
    }

    public CommandException(Throwable cause) {
        super(cause);
    }

    public CommandException(String message, Throwable cause) {
        super(message, cause);
    }
}
