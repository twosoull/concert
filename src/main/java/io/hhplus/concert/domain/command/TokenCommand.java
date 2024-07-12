package io.hhplus.concert.domain.command;

import io.hhplus.concert.common.enums.TokenStatus;
import io.hhplus.concert.domain.entity.Token;
import io.hhplus.concert.presentation.dto.TokenDto;
import io.hhplus.concert.presentation.mockApi.dto.TokenResDto;

import java.time.LocalDateTime;

public class TokenCommand {

    public record TokenReqDto () {}
    public record TokenCreateResDto(Long id, Long userId, Long order, LocalDateTime accessTime, TokenStatus status) {
    }
    public static TokenCreateResDto createOf(Token token, Long order){
        return new TokenCreateResDto(token.getId()
                ,token.getUser().getId()
                , order
                , token.getAccessTime()
                , token.getStatus());
    }
}
