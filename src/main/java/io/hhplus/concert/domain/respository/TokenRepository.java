package io.hhplus.concert.domain.respository;

import io.hhplus.concert.common.enums.TokenStatus;
import io.hhplus.concert.domain.command.TokenCommand;
import io.hhplus.concert.domain.entity.Token;

import java.util.List;

public interface TokenRepository {
    Token save(Token token);

    Token findByUserId(Long userId);

    Token findByToken(String token);

    List<Token> findAllByStatus(TokenStatus status);

    Token findByUserIdAndStatus(Long userId, TokenStatus tokenStatus);

    Token findTopStatusWait();

    TokenCommand.CheckTokenResultDto findByActiveTokenAndCountAndTopId();

    int updateTokenStatusActive(Long firstId, Long lastId);
}
