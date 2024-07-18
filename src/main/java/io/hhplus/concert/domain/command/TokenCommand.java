package io.hhplus.concert.domain.command;

import io.hhplus.concert.common.enums.TokenStatus;
import io.hhplus.concert.domain.entity.Token;

import java.time.LocalDateTime;

public class TokenCommand {

    public record TokenReqDto () {}
    public record TokenCreateResDto(Long id, Long userId, String token, Long order, LocalDateTime accessTime, TokenStatus status) {
    }
    public record CheckTokenResultDto(TokenStatus status, Long tokenId, int count){}
    public static TokenCreateResDto tokenInfo(Token token, Long order){
        return new TokenCreateResDto(token.getId()
                ,token.getUser().getId()
                , token.getToken()
                , order
                , token.getAccessTime()
                , token.getStatus());
    }
}
