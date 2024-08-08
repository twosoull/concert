package io.hhplus.concert.domain.respository;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.hhplus.concert.domain.entity.Token;
import io.hhplus.concert.domain.token.RedisToken;

import java.util.Set;

public interface RedisRepository {

    void save(String tokenUUID) throws JsonProcessingException;

    Long getTokenRank(String tokenUUID);

    Set<String> getTokenRange(int cnt);

    boolean isActiveMember(String tokenUUID);

    void activateTokens(int cnt);
}
