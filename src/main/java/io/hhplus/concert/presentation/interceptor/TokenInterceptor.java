package io.hhplus.concert.presentation.interceptor;

import io.hhplus.concert.common.enums.TokenStatus;
import io.hhplus.concert.domain.service.TokenService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component
@Slf4j
public class TokenInterceptor implements HandlerInterceptor {

    @Autowired
    private TokenService tokenService;

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
        }else{
            throw new RuntimeException("토큰이 없습니다.");
        }

        // 추출된 토큰 처리 로직
        System.out.println("추출 토큰: " + token);

        return true;
    }
}
