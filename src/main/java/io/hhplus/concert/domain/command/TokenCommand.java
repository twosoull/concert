package io.hhplus.concert.domain.command;

import io.hhplus.concert.common.enums.TokenStatus;
import io.hhplus.concert.domain.entity.Token;

import java.time.LocalDateTime;

public class TokenCommand {

    public record TokenReqDto () {}
    public record TokenCreateResDto(String token, Long order, int waitingTimeSeconds) {
    }
    public record CheckTokenResultDto(TokenStatus status, Long tokenId, int count){}
    public static TokenCreateResDto tokenInfo(String token, Long order, int waitingTimeSeconds){
        return new TokenCreateResDto(token,order, waitingTimeSeconds);
    }
}
