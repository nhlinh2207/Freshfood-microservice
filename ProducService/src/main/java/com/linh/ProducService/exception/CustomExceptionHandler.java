package com.linh.ProducService.exception;

import com.linh.ProducService.model.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

     @ExceptionHandler(ProductServiceCustomException.class)
      public ResponseEntity<ErrorResponse> productServiceExceptionHandler(ProductServiceCustomException exception){
          return new ResponseEntity<>(
                  ErrorResponse.builder()
                          .errorCode(exception.getErrorCode())
                          .errorMessage(exception.getMessage())
                          .build()
                  , HttpStatus.NOT_FOUND
          );
      }

}
