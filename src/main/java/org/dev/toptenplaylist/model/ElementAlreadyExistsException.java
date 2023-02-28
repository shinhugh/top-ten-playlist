package org.dev.toptenplaylist.model;

public class ElementAlreadyExistsException extends RuntimeException {
    public ElementAlreadyExistsException() {
        super("Element already exists");
    }

    public ElementAlreadyExistsException(String message) {
        super(message);
    }
}
