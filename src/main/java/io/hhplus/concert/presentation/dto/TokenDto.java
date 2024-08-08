package io.hhplus.concert.presentation.dto;

import io.hhplus.concert.domain.command.TokenCommand;

import java.time.LocalDateTime;

public class TokenDto {

    public record createTokenRequest (Long userId){}
    public record infoTokenRequest (String token){}
    public record infoTokenResponse(String token, Long order) {
    }
    public static TokenDto.infoTokenResponse infoResponse(TokenCommand.TokenCreateResDto token){
        return new TokenDto.infoTokenResponse(token.token()
                , token.order());
    }

    public record createTokenResponse(String token, Long order, int waitingTimeSeconds) {
    }
    public static TokenDto.createTokenResponse createResponse(TokenCommand.TokenCreateResDto token){
        return new TokenDto.createTokenResponse(token.token(), token.order(),token.waitingTimeSeconds());
    }
}
