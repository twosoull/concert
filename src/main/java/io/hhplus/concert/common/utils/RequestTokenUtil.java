package io.hhplus.concert.common.utils;


import io.hhplus.concert.domain.entity.Token;
import io.hhplus.concert.domain.respository.TokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import io.hhplus.concert.common.utils.RequestTokenHolder;

@Component
@RequiredArgsConstructor
public class RequestTokenUtil {

    private final RequestTokenHolder requestTokenHolder;
    private final TokenRepository tokenRepository;

    public String getCurrentToken() {

        return requestTokenHolder.getToken();
    }

    public Long getCurrentTokenUserId(){
        String  currentToken = getCurrentToken();
        return tokenRepository.findByToken(currentToken).getUser().getId();
    }
}