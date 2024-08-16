package io.hhplus.concert.presentation.dto;

import io.hhplus.concert.common.enums.TokenStatus;
import io.hhplus.concert.domain.command.TokenCommand;

import java.time.LocalDateTime;

public class TokenDto {

    public record createTokenRequest (Long userId){}
    public record infoTokenRequest (String token){}
    public record infoTokenResponse(Long id, Long userId, Long order, LocalDateTime accessTime, TokenStatus status) {
    }
    public static TokenDto.infoTokenResponse infoResponse(TokenCommand.TokenCreateResDto token){
        return new TokenDto.infoTokenResponse(token.id()
                ,token.id()
                , token.order()
                , token.accessTime()
                , token.status());
    }

    public record createTokenResponse(Long id, Long userId, String token, Long order, LocalDateTime accessTime, TokenStatus status) {
    }
    public static TokenDto.createTokenResponse createResponse(TokenCommand.TokenCreateResDto token){
        return new TokenDto.createTokenResponse(token.id()
                ,token.id()
                ,token.token()
                , token.order()
                , token.accessTime()
                , token.status());
    }
}
