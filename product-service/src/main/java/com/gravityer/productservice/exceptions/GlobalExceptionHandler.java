package com.gravityer.productservice.exceptions;

import com.gravityer.productservice.controller.BaseResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InternalErrorException.class)
    public ResponseEntity<BaseResponse> handleInternalErrorException(InternalErrorException ex) {
        BaseResponse response = new BaseResponse(false, ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<BaseResponse> handleNotFoundException(NotFoundException ex) {
        BaseResponse response = new BaseResponse(false, ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }
}
