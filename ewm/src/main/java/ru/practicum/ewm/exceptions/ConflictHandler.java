package ru.practicum.ewm.exceptions;

public class ConflictHandler extends RuntimeException {
    public ConflictHandler(String message) {
        super(message);
    }
}
