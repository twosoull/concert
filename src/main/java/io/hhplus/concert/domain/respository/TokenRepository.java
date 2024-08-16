package io.hhplus.concert.domain.respository;

import io.hhplus.concert.common.enums.TokenStatus;
import io.hhplus.concert.domain.entity.Token;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TokenRepository {
    Token save(Token token);

    Token findByUserId(Long userId);

    List<Token> findAllByStatus(TokenStatus status);

    Token findByUserIdAndStatus(Long userId, TokenStatus tokenStatus);

    Token findTopStatusWait();
}
