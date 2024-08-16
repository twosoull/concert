package io.hhplus.concert.infrastructure.repositoryImpl;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.hhplus.concert.domain.respository.RedisRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Set;

@Repository
@RequiredArgsConstructor
public class RedisRepositoryImpl implements RedisRepository {

    private final RedisTemplate<String, String> redisTemplate;

    private static final String WAITING_QUEUE_KEY = "waitingTokens";
    private static final String ACTIVE_SET_KEY = "activeTokens";

    @Override
    public void save(String tokenUUID) throws JsonProcessingException {
        LocalDateTime now = LocalDateTime.now();
        String formattedDate = now.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        double score = Double.parseDouble(formattedDate);

        ZSetOperations<String, String> zSetOps = redisTemplate.opsForZSet();
        zSetOps.add(WAITING_QUEUE_KEY, tokenUUID, score);
    }

    @Override
    public Long getTokenRank(String tokenUUID) {
        ZSetOperations<String, String> zSetOps = redisTemplate.opsForZSet();
        return zSetOps.rank(WAITING_QUEUE_KEY, tokenUUID);
    }

    public Set<String> getTokenRange(int cnt) {
        ZSetOperations<String, String> zSetOps = redisTemplate.opsForZSet();
        return zSetOps.range(WAITING_QUEUE_KEY, 0,cnt);
    }

    @Override
    public boolean isActiveMember(String tokenUUID) {
        SetOperations<String, String> setOps = redisTemplate.opsForSet();
        return setOps.isMember(ACTIVE_SET_KEY, tokenUUID);
    }

    @Override
    public void activateTokens(int cnt) {
        Set<String> tokens = getTokenRange(cnt);
        if (tokens == null || tokens.isEmpty()) {
            return;
        }

        SetOperations<String, String> setOps = redisTemplate.opsForSet();
        ZSetOperations<String, String> zSetOps = redisTemplate.opsForZSet();

        for (String token : tokens) {
            setOps.add(ACTIVE_SET_KEY, token);
            zSetOps.remove(WAITING_QUEUE_KEY, token);
        }
    }
}
