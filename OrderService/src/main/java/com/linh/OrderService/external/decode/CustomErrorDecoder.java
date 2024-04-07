package com.linh.OrderService.external.decode;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.linh.OrderService.exception.CustomException;
import com.linh.OrderService.external.response.ErrorResponse;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CustomErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String s, Response response) {
        ObjectMapper mapper = new ObjectMapper();
        log.info("Error url{}", response.request().url());
        log.info("Error header {}", response.request().headers());

        try{
            ErrorResponse errorResponse =
                    mapper.readValue(response.body().asInputStream(), ErrorResponse.class);
            return new CustomException(
                    errorResponse.getErrorCode(),
                    errorResponse.getErrorMessage(),
                    response.status()
            );
        }catch (Exception e){
            throw new CustomException(
                    "Internal Server Error",
                    "INTERNAL_SERVER_ERROR",
                    500
            );
        }
    }
}
