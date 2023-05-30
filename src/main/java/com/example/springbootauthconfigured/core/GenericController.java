package com.example.springbootauthconfigured.core;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

public abstract class GenericController {

    protected ResponseEntity<APIResponse> createAPIResponse(HttpStatus status, String message, Object data) {
        return ResponseEntity.status(status)
                .body(APIResponse.builder()
                        .timeStamp(LocalDateTime.now())
                        .data(data)
                        .message(message)
                        .statusCode(status.value())
                        .build()
                );
    }
}
