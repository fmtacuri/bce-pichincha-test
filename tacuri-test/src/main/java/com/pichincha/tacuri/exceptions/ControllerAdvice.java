package com.pichincha.tacuri.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

/**
 * @author fmtacuri
 * @version 1.1
 */
@RestControllerAdvice
public class ControllerAdvice extends ResponseEntityExceptionHandler {
    @ExceptionHandler(CustomException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ErrorMessage resourceNotFoundException(CustomException ex, WebRequest request) {
        return ErrorMessage.builder()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .timestamp(new Date())
                .message(ex.getMessage())
                .description(request.getDescription(false))
                .build();
    }
//
//    @ExceptionHandler(CustomException.class)
//    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
//    public ErrorMessage globalExceptionHandler(CustomException ex, WebRequest request) {
//        return ErrorMessage.builder()
//                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
//                .timestamp(new Date())
//                .message(ex.getMessage())
//                .description(request.getDescription(false))
//                .build();
//    }
//
//    @ExceptionHandler(CustomException.class)
//    @ResponseStatus(value = HttpStatus.NO_CONTENT)
//    public ErrorMessage resourceNoContentException(CustomException ex, WebRequest request) {
//        return ErrorMessage.builder()
//                .statusCode(HttpStatus.NO_CONTENT.value())
//                .timestamp(new Date())
//                .message(ex.getMessage())
//                .description(request.getDescription(false))
//                .build();
//    }
}

