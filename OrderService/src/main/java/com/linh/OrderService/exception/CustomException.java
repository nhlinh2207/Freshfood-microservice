package com.linh.OrderService.exception;

import lombok.*;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CustomException extends RuntimeException{

    private String errorCode;
    private int status;

    public CustomException(String errorCode, String message, int status){
        super(message);
        this.errorCode = errorCode;
        this.status = status;
    }
}
