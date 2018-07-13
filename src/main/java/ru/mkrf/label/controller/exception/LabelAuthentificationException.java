package ru.mkrf.label.controller.exception;

public class LabelAuthentificationException extends Exception {
    private String message;

    public LabelAuthentificationException(String message) {
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
