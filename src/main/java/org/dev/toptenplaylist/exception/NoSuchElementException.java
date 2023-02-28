package org.dev.toptenplaylist.exception;

public class NoSuchElementException extends RuntimeException {
    public NoSuchElementException() {
        super("No such element");
    }

    public NoSuchElementException(String message) {
        super(message);
    }
}
