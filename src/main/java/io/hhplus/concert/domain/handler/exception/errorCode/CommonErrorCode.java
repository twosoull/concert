package io.hhplus.concert.domain.handler.exception.errorCode;


import io.hhplus.concert.domain.handler.response.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum CommonErrorCode implements ErrorCode {

    INVALID_PARAMETER(HttpStatus.BAD_REQUEST.value(),HttpStatus.BAD_REQUEST, "파라미터 확인이 필요합니다."),
    RESOURCE_NOT_FOUND(HttpStatus.NOT_FOUND.value(),HttpStatus.NOT_FOUND, "조회된 값이 없습니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.NOT_FOUND.value(),HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error"),
    UNABLE_CALCULATE_ORDER(HttpStatus.NOT_FOUND.value(),HttpStatus.INTERNAL_SERVER_ERROR, "대기열 순서를 찾을 수 없습니다."),
    NEGATIVE_VALUE(HttpStatus.NOT_FOUND.value(),HttpStatus.INTERNAL_SERVER_ERROR, "대기열 순서를 찾을 수 없습니다.");

    private final int status;
    private final HttpStatus httpStatus;
    private final String message;

}