package io.hhplus.concert.domain.handler.response;

import org.springframework.http.HttpStatus;

public interface ErrorCode {

    String name();
    int getStatus();
    HttpStatus getHttpStatus();
    String getMessage();

}