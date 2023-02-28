package org.dev.toptenplaylist.model;

public class NoSuchElementException extends RuntimeException {
    public NoSuchElementException() {
        super("No such element");
    }

    public NoSuchElementException(String message) {
        super(message);
    }
}
