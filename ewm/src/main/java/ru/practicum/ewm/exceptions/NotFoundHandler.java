package ru.practicum.ewm.exceptions;

public class NotFoundHandler extends RuntimeException {
    public NotFoundHandler(String message) {
        super(message);
    }
}
