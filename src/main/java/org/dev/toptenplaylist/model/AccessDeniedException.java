package org.dev.toptenplaylist.model;

public class AccessDeniedException extends RuntimeException {
    public AccessDeniedException() {
        super("Access denied");
    }

    public AccessDeniedException(String message) {
        super(message);
    }
}
