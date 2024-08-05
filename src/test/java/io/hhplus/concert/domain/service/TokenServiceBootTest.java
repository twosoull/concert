package io.hhplus.concert.domain.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TokenServiceBootTest {

    @Autowired
    TokenService tokenService;

    @Test
    @DisplayName("토큰대기열 -액티브 관련 카운트 조회")
    void activeTokenIfInOrder_test() {
        //tokenService.activeTokenIfInOrder();
    }

    @Test
    @DisplayName("대기열 액티브 토큰으로 전환 테스트")
    void convertTokens() {

        tokenService.convertTokens();
    }

}
