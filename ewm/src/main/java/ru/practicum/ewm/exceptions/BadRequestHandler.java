package ru.practicum.ewm.exceptions;

public class BadRequestHandler extends RuntimeException {
    public BadRequestHandler(String message) {
        super(message);
    }
}
