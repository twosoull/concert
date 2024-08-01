package io.hhplus.concert.domain.service;

import io.hhplus.concert.common.enums.TokenStatus;
import io.hhplus.concert.domain.command.TokenCommand;
import io.hhplus.concert.domain.entity.Token;
import io.hhplus.concert.domain.entity.User;
import io.hhplus.concert.domain.handler.exception.TokenException;
import io.hhplus.concert.domain.respository.TokenRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import static io.hhplus.concert.domain.handler.exception.errorCode.CommonErrorCode.RESOURCE_NOT_FOUND;


@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class TokenService {

    private final TokenRepository tokenRepository;

    public TokenCommand.TokenCreateResDto getTokenInfo(String token) {

        Token findToken = tokenRepository.findByToken(token);

        if (findToken.getStatus() == TokenStatus.WAIT ){
            long order = findOrder(findToken);
            return TokenCommand.tokenInfo(findToken, order);
        }
        return TokenCommand.tokenInfo(findToken, 0L);
    }

    public TokenCommand.TokenCreateResDto createToken(Long userId) {
        //유저가 없으므로 자동생성 유저
        User user = new User(userId, "1234", "향해99");

        //토큰 active 수 파악
        TokenStatus status = getTokenStatus();

        Token token = tokenRepository.findByUserId(userId);
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

            return TokenCommand.tokenInfo(token, order);
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

            //만료일 경우 다시 생성 로직 넣어야 함
            return TokenCommand.tokenInfo(saveToken, order);
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
            List<Token> waitTokens = tokenRepository.findAllByStatus(TokenStatus.WAIT);
            if( waitTokens.size() == 0){
                return TokenStatus.ACTIVE;
            }
        }
        return TokenStatus.WAIT;
    }

    public void activeTokenIfInOrder(){
        TokenCommand.CheckTokenResultDto resultDto = tokenRepository.findByActiveTokenAndCountAndTopId();

        TokenStatus status = resultDto.status();
        int activeCount = resultDto.count();
        Long activeLastUserId = resultDto.tokenId();

        if( activeCount < 30) {
            Long passCount = 30L - activeCount;
            Long firstId = activeLastUserId + 1;
            Long lastId = activeLastUserId + passCount;

            int i = tokenRepository.updateTokenStatusActive(firstId, lastId);
        }

    }


    //토큰 시간이 5분 지났는지 판단한다.
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
        long order = token.getId() - topToken.getId() ;

        if (order < 0L) {
            throw new TokenException(RESOURCE_NOT_FOUND);
        }

        return order;
    }

    //토큰 검사 후 전환하기
    public void convertTokens() {
        List<Token> tokens = tokenRepository.findAllByStatus(TokenStatus.ACTIVE);
        if (tokens.size() < 30) {
            int activationPossible = 30 - tokens.size();
            List<Token> waitTokens = tokenRepository.findAllByStatus(TokenStatus.WAIT);

            for(int i = 0; i < activationPossible; i++){
                LocalDateTime now = LocalDateTime.now();
                Token token = waitTokens.get(i);
                token.setStatus(TokenStatus.ACTIVE);
                token.setUpdateAt(now);

                log.info("[토큰 전환 성공] 대상 유저 = {} , token = {}, 생성 시각 = {}, 전환 시각 ={}"
                        ,token.getUser().getId()
                        ,token.getToken()
                        ,token.getAccessTime()
                        ,token.getUpdateAt());
            }
        }
    }

    /*대용량 때의 시나리오 예상*/
        //info와 체크는 일단 필요가 없다. 나는 대기시간 등을 가정하는 방식을 통해서 진행할 것이다.
        //1) 스케쥴링이 도는 시간을 고려해 대기인원의 토큰 발급시간 등을 대입해 ~~초마다 들여보낼 것이다.
        //2) 사용자는 폴링을 계속 할테지만 자신의 발급시간, 가장 빨랏던 순번의 발급시간 + 10초 마다 들여보냄 스택을쌓아서 계산함
        //3) 스택은 얼마나 기다렸는지에 대한 표시로 10초마다 호출한다면 1씩 쌓인다.
        // 내 발급시간 + 스택시간 - 가장빠른 발급시간이 0이 되면 db를 호출하고 들여보낸다 (active로 변경)
        //스케줄러로 토큰 만료
}
