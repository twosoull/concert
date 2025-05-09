package io.hhplus.concert.common.utils;


import io.hhplus.concert.domain.entity.Token;
import io.hhplus.concert.domain.respository.TokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RequestTokenUtil {

    private final RequestTokenHolder requestTokenHolder;
    private final TokenRepository tokenRepository;

    public Token getCurrentToken() {
        String tokenValue = requestTokenHolder.getToken();
        if (tokenValue != null) {
            return tokenRepository.findByToken(tokenValue);
        }
        return null; // 또는 예외를 던질 수 있습니다.
    }

    public Long getCurrentTokenUserId(){
        Token currentToken = getCurrentToken();
        return currentToken.getUser().getId();
    }
}