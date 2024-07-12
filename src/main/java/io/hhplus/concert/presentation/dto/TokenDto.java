package io.hhplus.concert.presentation.dto;

public class TokenDto {

    public record createTokenRequest (Long userId){}
    public record infoTokenRequest (Long userId){}
    public record createTokenResponse () {
    }
}
