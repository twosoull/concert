package io.hhplus.concert.domain.service;

import io.hhplus.concert.common.enums.TokenStatus;
import io.hhplus.concert.domain.command.TokenCommand;
import io.hhplus.concert.domain.entity.Token;
import io.hhplus.concert.domain.entity.User;
import io.hhplus.concert.domain.respository.TokenRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;


@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class TokenService {

    private final TokenRepository tokenRepository;

    public TokenCommand.TokenCreateResDto info(Long userId) {

        Token token = tokenRepository.findByUserId(userId);

        if (token.getStatus() == TokenStatus.WAIT ){
            long order = findOrder(token);
            return TokenCommand.createOf(token, order);
        }
        return TokenCommand.createOf(token, 0L);
    }

    public TokenCommand.TokenCreateResDto create(Long userId) {
        //유저가 없으므로 자동생성 유저
        User user = new User(userId, "1234", "향해99");

        //토큰 active 수 파악
        TokenStatus status = getTokenStatus();

        Token token = tokenRepository.findByUserIdAndStatus(userId, TokenStatus.WAIT);
        //토큰이 이미 존재할 경우
        if (!ObjectUtils.isEmpty(token)) {
            //토큰이 이미 active일 경우에는 updateAt 비교
            //토큰이 Wait라면 accessTime 비교
            //5분이 지났으면
            if (isTokenExpired(token)) {
                token.setStatus(TokenStatus.EXPIRATION);
            }

            Long order = 0L;
            if (token.getStatus() != TokenStatus.ACTIVE) {
                order = findOrder(token);
            }

            return TokenCommand.createOf(token, order);
        } else {
            //토큰 발급
            Token saveToken = tokenRepository.save(Token.create(user, status));

            if (status == TokenStatus.ACTIVE) {
                saveToken.setUpdateAt(LocalDateTime.now());
            }

            Long order = 0L;
            if (saveToken.getStatus() != TokenStatus.ACTIVE) {
                order = findOrder(saveToken);
            }


            return TokenCommand.createOf(saveToken, order);
        }
    }

    private static LocalDateTime getLocalDateTime(Token token) {
        LocalDateTime issuedAt =
                token.getStatus() == TokenStatus.ACTIVE ? token.getUpdateAt()
                        : token.getAccessTime();

        return issuedAt;
    }


    //토큰 active 수 파악 후 현재 스테이터스 확인
    private TokenStatus getTokenStatus() {
        List<Token> tokens = tokenRepository.findAllByStatus(TokenStatus.ACTIVE);
        if (tokens.size() < 30) {
            return TokenStatus.ACTIVE;
        }
        return TokenStatus.WAIT;
    }

    public boolean isTokenExpired(Token token) {
        LocalDateTime issuedAt =
                token.getStatus() == TokenStatus.ACTIVE ? token.getUpdateAt()
                        : token.getAccessTime();

        LocalDateTime now = LocalDateTime.now();
        Duration duration = Duration.between(issuedAt, now);
        return duration.toMinutes() >= 5;
    }

    //내 순번 찾기
    public long findOrder(Token token) {
        Token topToken = tokenRepository.findTopStatusWait();
        long order = token.getId() - topToken.getId();

        if (order < 0L) {
            throw new RuntimeException("잘못된 요청 입니다.");
        }

        return order;
    }


    /*대용량 때의 시나리오 예상*/
        //info와 체크는 일단 필요가 없다. 나는 대기시간 등을 가정하는 방식을 통해서 진행할 것이다.
        //1) 스케쥴링이 도는 시간을 고려해 대기인원의 토큰 발급시간 등을 대입해 ~~초마다 들여보낼 것이다.
        //2) 사용자는 폴링을 계속 할테지만 자신의 발급시간, 가장 빨랏던 순번의 발급시간 + 10초 마다 들여보냄 스택을쌓아서 계산함
        //3) 스택은 얼마나 기다렸는지에 대한 표시로 10초마다 호출한다면 1씩 쌓인다.
        // 내 발급시간 + 스택시간 - 가장빠른 발급시간이 0이 되면 db를 호출하고 들여보낸다 (active로 변경)
    //스케줄러로 토큰 만료

}
