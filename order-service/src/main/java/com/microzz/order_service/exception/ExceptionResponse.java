package com.microzz.order_service.exception;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExceptionResponse {


    private String timestamp;
    private int statusCode;
    private String message;

    public ExceptionResponse(String timestamp, int statusCode, String message) {
        this.timestamp = timestamp;
        this.statusCode = statusCode;
        this.message = message;
    }

}
