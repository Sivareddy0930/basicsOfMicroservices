package com.microzz.order_service.exception;

import com.microzz.order_service.dto.APIResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
public class ServerUnavailableException extends RuntimeException{


    public ServerUnavailableException(String message) {
        super(message);

    }

}
