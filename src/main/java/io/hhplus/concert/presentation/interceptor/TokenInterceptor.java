package io.hhplus.concert.presentation.interceptor;

import io.hhplus.concert.common.enums.TokenStatus;
import io.hhplus.concert.domain.entity.Token;
import io.hhplus.concert.domain.handler.exception.TokenException;
import io.hhplus.concert.domain.respository.TokenRepository;
import io.hhplus.concert.domain.service.TokenService;
import io.hhplus.concert.presentation.holder.RequestTokenHolder;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.time.Duration;
import java.time.LocalDateTime;

import static io.hhplus.concert.domain.handler.exception.errorCode.CommonErrorCode.EXPIRED_TOKEN;
import static io.hhplus.concert.domain.handler.exception.errorCode.CommonErrorCode.NOT_FOUND_TOKEN;

@Component
@Slf4j
@RequiredArgsConstructor
public class TokenInterceptor implements HandlerInterceptor {

    private final RequestTokenHolder requestTokenHolder;
    private final TokenRepository tokenRepository;

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        log.info("afterCompletion");
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        log.info("postHandle");
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("preHandle");

        // 요청 헤더에서 토큰 추출
        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7); // "Bearer " 부분 제거
            requestTokenHolder.setToken(token);
        }else{
            throw new TokenException(NOT_FOUND_TOKEN);
        }
        Token findToken = tokenRepository.findByToken(token);
        if(isTokenExpired(findToken)){
            findToken.setStatus(TokenStatus.EXPIRATION);
            throw new TokenException(EXPIRED_TOKEN);
        }

        return true;
    }

    //액티브 된 토큰의 시간이 5분 지났는지 판단 한다.
    public boolean isTokenExpired(Token token) {
        LocalDateTime issuedAt =
                token.getStatus() == TokenStatus.ACTIVE ? token.getUpdateAt()
                        : token.getAccessTime();

        LocalDateTime now = LocalDateTime.now();
        Duration duration = Duration.between(issuedAt, now);
        return duration.toMinutes() >= 5;
    }
}
