package ru.practicum.ewm.exceptions;

public class InternalServerErrorHandler extends RuntimeException {
    public InternalServerErrorHandler(String message) {
        super(message);
    }
}
