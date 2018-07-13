package ru.mkrf.label.controller.exception;

public class LabelAuthorizationException extends Exception {
    private String message;

    public LabelAuthorizationException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
