package io.hhplus.concert.presentation.scheduler;

import io.hhplus.concert.domain.service.TokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class TokenScheduler {

    private final TokenService tokenService;

    //액티브 토큰 만료시간 확인
    //@Scheduled(fixedDelay = 1000)
    public void convertTokens() {
        log.info("[Scheduled] convertTokens -- start");
        tokenService.convertTokens();
        log.info("[Scheduled] convertTokens -- end");
    }
}
