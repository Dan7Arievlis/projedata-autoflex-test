package io.github.dan7arievlis.autoflextest.controller.common;

import io.github.dan7arievlis.autoflextest.controller.dto.error.ErrorField;
import io.github.dan7arievlis.autoflextest.controller.dto.error.ErrorResponse;
import io.github.dan7arievlis.autoflextest.exceptions.DuplicatedRegisterException;
import io.github.dan7arievlis.autoflextest.exceptions.InvalidFieldException;
import io.github.dan7arievlis.autoflextest.exceptions.OperationNotAllowedException;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ErrorResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error("Validation error: {}", e.getMessage());
        var errorList = e.getFieldErrors()
                .stream()
                .map(fe -> new ErrorField(
                        fe.getField(),
                        fe.getDefaultMessage()
                )).toList();

        return new ErrorResponse(HttpStatus.UNPROCESSABLE_ENTITY.value(), "Validation error.", errorList);
    }

    @ExceptionHandler(DuplicatedRegisterException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleDuplicatedRegisterException(DuplicatedRegisterException e) {
        log.error("Conflict error: {}", e.getMessage());
        return ErrorResponse.conflict(e.getMessage());
    }

    @ExceptionHandler(OperationNotAllowedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleOperationNotAllowedException(OperationNotAllowedException e) {
        log.error("Operational error: {}", e.getMessage());
        return ErrorResponse.defaultResponse(e.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleIllegalArgumentException(IllegalArgumentException e) {
        log.error("Illegal argument error: {}", e.getMessage());
        return ErrorResponse.defaultResponse(e.getMessage());
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleUsernameNotFoundException(UsernameNotFoundException e) {
        log.error("Username not found error: {}", e.getMessage());
        return ErrorResponse.defaultResponse(e.getMessage());
    }

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<?> handleEntityNotFoundException(EntityNotFoundException e) {
        log.error("Not found error: {}", e.getMessage());
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(InvalidFieldException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ErrorResponse handleInvalidFieldException(InvalidFieldException e) {
        log.error("Field validation error: {}", e.getMessage());
        return new ErrorResponse(HttpStatus.UNPROCESSABLE_ENTITY.value(),
                "Validation error.",
                List.of(new ErrorField(e.getField(), e.getMessage())));
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorResponse handleAccessDeniedException(AccessDeniedException e) {
        log.error("Authentication error: {}", e.getMessage());
        return new ErrorResponse(HttpStatus.FORBIDDEN.value(), "Access denied.", List.of());
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse unhandledErrors(RuntimeException e) {
        log.error("Unexpected error", e);
        return new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Unexpected error occurred.", List.of());
    }
}
