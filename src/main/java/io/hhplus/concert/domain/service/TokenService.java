package io.hhplus.concert.domain.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.hhplus.concert.common.utils.RequestTokenUtil;
import io.hhplus.concert.domain.command.TokenCommand;
import io.hhplus.concert.domain.entity.Token;
import io.hhplus.concert.domain.entity.User;
import io.hhplus.concert.domain.handler.exception.RestApiException;
import io.hhplus.concert.domain.respository.RedisRepository;
import io.hhplus.concert.domain.respository.TokenRepository;

import io.hhplus.concert.domain.respository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Set;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import static io.hhplus.concert.domain.handler.exception.errorCode.CommonErrorCode.TOKEN_ALREADY_CREATE;


@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class TokenService {

    private static final Logger logger = Logger.getLogger(TokenService.class.getName());

    static {
        try {
            // MacOS의 경우, 로그 파일의 경로를 설정합니다.
            FileHandler fileHandler = new FileHandler("/Users/iyeonghun/Desktop/Project/logs/token_service.log", true);
            fileHandler.setFormatter(new SimpleFormatter());
            logger.addHandler(fileHandler);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private final TokenRepository tokenRepository;
    private final RequestTokenUtil requestTokenUtil;
    private final RedisRepository redisRepository;
    private final UserRepository userRepository;
    public TokenCommand.TokenCreateResDto getTokenInfo() {
        String currentToken = requestTokenUtil.getCurrentToken();
        boolean isActive = redisRepository.isActiveMember(currentToken);
        //액티브에 있으면
        if (isActive) {
            //-1로 주어 다음 url에 넘어갈 수 있게 준다.
            return TokenCommand.tokenInfo(currentToken,-1L , -1);
        }

        //토큰 WaitngToken 유무 및 순서 확인
        Long tokenRank = redisRepository.getTokenRank(currentToken);

        //대기시간
        int waitingTimeSeconds = waitingTimeSeconds(tokenRank);
        return TokenCommand.tokenInfo(currentToken,tokenRank, waitingTimeSeconds);
    }

    public TokenCommand.TokenCreateResDto  createToken(Long userId) throws JsonProcessingException {
        String tokenUUID = "";
        Long tokenOrder = 0L;
        int waitingTimeSeconds = 0;
        try {
            //토큰 발급
            tokenUUID = Token.create();
            redisRepository.save(tokenUUID);


            Token findbyUserIdToken = tokenRepository.findByUserId(userId);
            if (findbyUserIdToken != null) {
                throw new RestApiException(TOKEN_ALREADY_CREATE);
            }
            User user = new User(userId, "1234", "홍식이");

            Token token = new Token(tokenUUID, user);
            tokenRepository.save(token);

            //순서조회
            tokenOrder = redisRepository.getTokenRank(tokenUUID);

            //대기시간
            waitingTimeSeconds = waitingTimeSeconds(tokenOrder);


        } catch(Exception e){
            // 예외 발생 시 로그를 파일에 기록
            logger.severe("Error occurred: " + e.getMessage());
            e.printStackTrace();
        }
        return TokenCommand.tokenInfo(tokenUUID,tokenOrder,waitingTimeSeconds);
    }

    public int waitingTimeSeconds(Long tokenOrder) {
        int position = Math.toIntExact(tokenOrder); // 사용자의 입장 순서
        int intervalInSeconds = 10; // 활성화 간격 (초)
        int usersPerInterval = 1000; // 한 번에 활성화되는 사용자 수

        int waitingTimeInSeconds = calculateWaitingTime(position, intervalInSeconds, usersPerInterval);
        return waitingTimeInSeconds;
}

    public  int calculateWaitingTime(int position, int intervalInSeconds, int usersPerInterval) {
        int intervalsNeeded = (int) Math.ceil((double) position / usersPerInterval);
        return intervalsNeeded * intervalInSeconds;
    }

    //redis 사용
    public void convertTokens() {
        int usersPerInterval = 10; // 한 번에 활성화되는 사용자 수
        Set<String> tokens = redisRepository.getTokenRange(usersPerInterval);
        if (tokens == null || tokens.isEmpty()) {
            return;
        }
        redisRepository.activateTokens(30);
    }

    /*
    private static LocalDateTime getLocalDateTime(Token token) {
        LocalDateTime issuedAt =
                token.getStatus() == TokenStatus.ACTIVE ? token.getUpdateAt()
                        : token.getAccessTime();
        return issuedAt;
    }
*/
    //토큰 active 수 파악 후 현재 스테이터스 확인
    /*
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
*/
    /*
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
*/

    //토큰 시간이 5분 지났는지 판단한다.
    /*
    public boolean isTokenExpired(Token token) {

        LocalDateTime issuedAt =
                token.getStatus() == TokenStatus.ACTIVE ? token.getUpdateAt()
                        : token.getAccessTime();

        LocalDateTime now = LocalDateTime.now();
        Duration duration = Duration.between(issuedAt, now);
        return duration.toMinutes() >= 5;

        return false;

    }*/

    //내 순번 찾기
    /*
    public long findOrder(Token token) {
        Token topToken = tokenRepository.findTopStatusWait();
        long order = token.getId() - topToken.getId() ;

        if (order < 0L) {
            throw new TokenException(RESOURCE_NOT_FOUND);
        }

        return order;
    }
*/
    //토큰 검사 후 전환하기

    /*
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
*/
}
