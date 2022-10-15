package ru.practicum.ewm.exceptions;

public class ForbiddenHandler extends RuntimeException {
    public ForbiddenHandler(String message) {
        super(message);
    }
}
