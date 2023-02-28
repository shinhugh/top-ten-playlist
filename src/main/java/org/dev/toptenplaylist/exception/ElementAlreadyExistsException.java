package org.dev.toptenplaylist.exception;

public class ElementAlreadyExistsException extends RuntimeException {
    public ElementAlreadyExistsException() {
        super("Element already exists");
    }

    public ElementAlreadyExistsException(String message) {
        super(message);
    }
}
