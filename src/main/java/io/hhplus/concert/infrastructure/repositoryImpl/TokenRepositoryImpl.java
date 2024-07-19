package io.hhplus.concert.infrastructure.repositoryImpl;

import io.hhplus.concert.common.enums.TokenStatus;
import io.hhplus.concert.domain.command.TokenCommand;
import io.hhplus.concert.domain.entity.Token;
import io.hhplus.concert.domain.respository.TokenRepository;
import io.hhplus.concert.infrastructure.repositoryORM.TokenJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class TokenRepositoryImpl implements TokenRepository {

    private final TokenJpaRepository tokenJpaRepository;

    @Override
    public Token save(Token token) {
        return tokenJpaRepository.save(token);
    }

    @Override
    public Token findByUserId(Long userId) {
        return tokenJpaRepository.findByUserId(userId);
    }

    @Override
    public Token findByToken(String token) {
        return tokenJpaRepository.findByToken(token);
    }

    @Override
    public List<Token> findAllByStatus(TokenStatus status) {
        return tokenJpaRepository.findAllByStatus(status);
    }

    @Override
    public Token findByUserIdAndStatus(Long userId, TokenStatus tokenStatus) {
        return tokenJpaRepository.findByUserIdAndStatus(userId, tokenStatus);
    }

    @Override
    public Token findTopStatusWait() {
        return tokenJpaRepository.findTopStatusWait();
    }

    @Override
    public TokenCommand.CheckTokenResultDto findByActiveTokenAndCountAndTopId() {
        return tokenJpaRepository.findByActiveTokenAndCountAndTopId();
    }

    @Override
    public int updateTokenStatusActive(Long firstId, Long lastId) {
        return tokenJpaRepository.updateTokenStatusActive(firstId, lastId);
    };
}
