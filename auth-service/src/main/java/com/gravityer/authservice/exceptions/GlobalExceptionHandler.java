package com.gravityer.authservice.exceptions;

import com.gravityer.authservice.controllers.BaseResponse;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.Objects;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<BaseResponse<String>> handleUsernameNotFoundException(UsernameNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new BaseResponse<>(ex.getMessage()));
    }

    @ExceptionHandler(InternalErrorException.class)
    public ResponseEntity<BaseResponse<String>> handleInternalErrorException(InternalErrorException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new BaseResponse<>(ex.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<BaseResponse<List<String>>> handleMissingArguments(MethodArgumentNotValidException exception) {
        List<String> errors = exception.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .toList();
        return new ResponseEntity<>(new BaseResponse<>(false, "Required Argument Missing", errors), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<BaseResponse<Void>> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        BaseResponse<Void> response = new BaseResponse<>(false, Objects.requireNonNull(ex.getRootCause()).getMessage().substring(ex.getRootCause().getMessage().indexOf("Detail: ") + 8));
        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

}
