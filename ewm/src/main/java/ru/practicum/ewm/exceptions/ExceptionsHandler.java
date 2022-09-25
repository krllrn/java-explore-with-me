package ru.practicum.ewm.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class ExceptionsHandler {

    @ExceptionHandler(BadRequestHandler.class)
    public ResponseEntity<ApiError> badRequest(BadRequestHandler ex) {
        ApiError apiError = new ApiError();
        apiError.setTimestamp(LocalDateTime.now());
        apiError.setStatus(HttpStatus.BAD_REQUEST);
        apiError.setReason(ex.getMessage());
        apiError.setMessage("The server cannot or will not process the request due to something that is perceived to be a client error");

        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ForbiddenHandler.class)
    public ResponseEntity<ApiError> forbidden(ForbiddenHandler ex) {
        ApiError apiError = new ApiError();
        apiError.setTimestamp(LocalDateTime.now());
        apiError.setStatus(HttpStatus.FORBIDDEN);
        apiError.setReason(ex.getMessage());
        apiError.setMessage("The client does not have access rights to the content; that is, it is unauthorized, " +
                "so the server is refusing to give the requested resource.");

        return new ResponseEntity<>(apiError, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(ConflictHandler.class)
    public ResponseEntity<ApiError> conflict(ConflictHandler ex) {
        ApiError apiError = new ApiError();
        apiError.setTimestamp(LocalDateTime.now());
        apiError.setStatus(HttpStatus.CONFLICT);
        apiError.setReason(ex.getMessage());
        apiError.setMessage("Request conflicts with the current state of the server.");

        return new ResponseEntity<ApiError>(apiError, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(NotFoundHandler.class)
    public ResponseEntity<ApiError> notFound(NotFoundHandler ex) {
        ApiError apiError = new ApiError();
        apiError.setTimestamp(LocalDateTime.now());
        apiError.setStatus(HttpStatus.NOT_FOUND);
        apiError.setReason(ex.getMessage());
        apiError.setMessage("The server can not find the requested resource.");

        return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InternalServerErrorHandler.class)
    public ResponseEntity<ApiError> internalServerError(InternalServerErrorHandler ex) {
        ApiError apiError = new ApiError();
        apiError.setTimestamp(LocalDateTime.now());
        apiError.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        apiError.setReason(ex.getMessage());
        apiError.setMessage("The server has encountered a situation it does not know how to handle.");

        return new ResponseEntity<>(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
