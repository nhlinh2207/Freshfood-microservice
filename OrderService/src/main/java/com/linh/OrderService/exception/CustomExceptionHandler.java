package com.linh.OrderService.exception;

import com.linh.OrderService.external.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

      @ExceptionHandler(CustomException.class)
      public ResponseEntity<ErrorResponse> productServiceExceptionHandler(CustomException exception){
          return new ResponseEntity<>(
                  ErrorResponse.builder()
                          .errorCode(exception.getErrorCode())
                          .errorMessage(exception.getMessage())
                          .build()
                  , HttpStatus.valueOf(exception.getStatus())
          );
      }

}
