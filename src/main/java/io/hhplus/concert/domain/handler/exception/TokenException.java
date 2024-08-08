package io.hhplus.concert.domain.handler.exception;

import io.hhplus.concert.domain.handler.response.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class TokenException extends RuntimeException{
    private final ErrorCode errorCode;
}
