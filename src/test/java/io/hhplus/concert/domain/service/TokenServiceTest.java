package io.hhplus.concert.domain.service;

import io.hhplus.concert.common.enums.TokenStatus;
import io.hhplus.concert.domain.command.TokenCommand;
import io.hhplus.concert.domain.entity.Token;
import io.hhplus.concert.domain.entity.User;
import io.hhplus.concert.domain.respository.TokenRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class TokenServiceTest {

    @InjectMocks
    TokenService tokenService;

    @Mock
    TokenRepository tokenRepository;

    @Test
    @DisplayName("토큰 조회 - Wait")
    void info_Wait() {
        //given
        Long userId = 1L;
        String tokenUUID = "topTokenId";
        User user = new User(userId, "1234", "향해99");

        Long tokenId = 50L;
        Long topTokenId = 30L;
        TokenStatus tokenStatus = TokenStatus.WAIT;

        Token token = new Token(tokenId,tokenUUID,user,"url",LocalDateTime.now()
                ,tokenStatus,LocalDateTime.now(),LocalDateTime.now());
        doReturn(token).when(tokenRepository).findByUserId(any(Long.class));

        Token topToken = new Token(topTokenId,"topTokenId",null,"url",LocalDateTime.now(),TokenStatus.WAIT,LocalDateTime.now(),LocalDateTime.now());
        doReturn(topToken).when(tokenRepository).findTopStatusWait();

        //when
        TokenCommand.TokenCreateResDto TokenResDto = tokenService.getTokenInfo();

        //assertEquals(tokenStatus, TokenResDto.status());
        assertEquals(tokenId - topTokenId, TokenResDto.order());
    }

    @Test
    @DisplayName("토큰 조회 - Active")
    void info_Active() {
        //given
        Long userId = 1L;
        String tokenUUID = "topTokenId";
        User user = new User(userId, "1234", "향해99");

        Long tokenId = 50L;
        TokenStatus tokenStatus = TokenStatus.ACTIVE;

        Token token = new Token(tokenId,tokenUUID,user,"url",LocalDateTime.now()
                ,tokenStatus,LocalDateTime.now(),LocalDateTime.now());
        doReturn(token).when(tokenRepository).findByUserId(any(Long.class));

        //when
        TokenCommand.TokenCreateResDto TokenResDto = tokenService.getTokenInfo();

        //assertEquals(tokenStatus, TokenResDto.status());
        assertEquals(0L, TokenResDto.order());
    }


    @Test
    @DisplayName("토큰 생성 테스트 -- 신규생성 Wait 토큰")
    void create_test() {
        //given
        Long userId = 1L;
        Long findUserId = 31L;
        Long topTokenId = 31L;
        Long saveTokenId = 50L;
        User user = new User(userId, "1234", "향해99");
        User finduser = new User(findUserId, "123456", "향해오만번함");

        /*이미 등록 되어 있지 않음*/
     //   doReturn(null).when(tokenRepository).findByUserIdAndStatus(any(Long.class),any(TokenStatus.class));

        /*ACTIVE 토큰의 수가 30이상이면 WAIT*/
       /*
        List<Token> tokens = new ArrayList<>();
        for(Long i = 0L ; i < 31L ; i++){
            tokens.add(new Token(i, "listToken", null, null, null, null, null, null));
        }

        doReturn(tokens).when(tokenRepository).findAllByStatus(TokenStatus.ACTIVE);
        Token token = new Token(saveTokenId,"saveTokenId",user,"url",LocalDateTime.now(),TokenStatus.WAIT,LocalDateTime.now(),LocalDateTime.now());
        doReturn(token).when(tokenRepository).save(any(Token.class));

        Token topToken = new Token(topTokenId,"topTokenId",null,"url",LocalDateTime.now(),TokenStatus.WAIT,LocalDateTime.now(),LocalDateTime.now());
        doReturn(topToken).when(tokenRepository).findTopStatusWait();



        //when
        TokenCommand.TokenCreateResDto TokenResDto = tokenService.createToken(userId);

        //then
        //assertEquals(userId,TokenResDto.userId());
        //assertEquals(TokenStatus.WAIT,TokenResDto.status());
        assertEquals(saveTokenId - topTokenId, TokenResDto.order());

        */
    }

    //이미 있는 토큰




    @Test
    @DisplayName("토큰 생성 테스트 -- 신규 생성 Active 토큰 ")
    void create_test_Active() {
        /*
        //given
        Long userId = 1L;
        Long findUserId = 31L;
        Long topTokenId = 31L;
        Long saveTokenId = 50L;
        User user = new User(userId, "1234", "향해99");
        User finduser = new User(findUserId, "123456", "향해오만번함");

        //이미 등록 되어 있지 않음
        doReturn(null).when(tokenRepository).findByUserIdAndStatus(any(Long.class),any(TokenStatus.class));

        //ACTIVE 토큰의 수가 30 이하면 ACTIVE
        List<Token> tokens = new ArrayList<>();

        doReturn(tokens).when(tokenRepository).findAllByStatus(TokenStatus.ACTIVE);
        Token token = new Token(saveTokenId,"saveTokenId",user,"url",LocalDateTime.now(),TokenStatus.ACTIVE,LocalDateTime.now(),LocalDateTime.now());
        doReturn(token).when(tokenRepository).save(any(Token.class));

        //when
        TokenCommand.TokenCreateResDto TokenResDto = tokenService.createToken(userId);

        //then
        //assertEquals(userId,TokenResDto.userId());
        //assertEquals(TokenStatus.ACTIVE,TokenResDto.status());
        assertEquals(0L, TokenResDto.order());
        */
    }

    @Test
    @DisplayName("토큰 생성 테스트 -- 기존토큰 Wait")
    void create_test_already() {
        /*
        //given
        Long userId = 1L;
        Long findUserId = 2L;
        Long findTokenId = 40L;
        Long topTokenId = 31L;
        Long saveTokenId = 50L;
        User user = new User(userId, "1234", "향해99");
        User finduser = new User(findUserId, "123456", "향해오만번함");
        TokenStatus findTokenStatus = TokenStatus.WAIT;

        Token findToken = new Token(findTokenId,"topTokenId",finduser,"url",LocalDateTime.now()
                ,findTokenStatus,LocalDateTime.now(),LocalDateTime.now());

        //이미 등록 되어 있음
        doReturn(findToken).when(tokenRepository).findByUserIdAndStatus(any(Long.class),any(TokenStatus.class));

        //파인드 에서는 필요가 없음
        List<Token> tokens = new ArrayList<>();
        for(Long i = 0L ; i < 31L ; i++){
            tokens.add(new Token(i, "listToken", null, null, null, null, null, null));
        }
        doReturn(tokens).when(tokenRepository).findAllByStatus(TokenStatus.ACTIVE);

        //findToken이 WAIT면
        Token topToken = new Token(topTokenId,"topTokenId",null,"url",LocalDateTime.now(),TokenStatus.WAIT,LocalDateTime.now(),LocalDateTime.now());
        doReturn(topToken).when(tokenRepository).findTopStatusWait();

        //when
        TokenCommand.TokenCreateResDto TokenResDto = tokenService.createToken(userId);

        //then
        //assertEquals(findUserId,TokenResDto.userId());
        //assertEquals(TokenStatus.WAIT,TokenResDto.status());
        assertEquals(findTokenId - topTokenId, TokenResDto.order());
        */
    }

    //이미 있는 토큰




    @Test
    @DisplayName("토큰 생성 테스트 -- 신규 생성 Active 토큰 ")
    void create_test_already_Active() {
        /*
        //given
        Long userId = 1L;
        Long findUserId = 2L;
        Long findTokenId = 40L;
        Long topTokenId = 31L;
        Long saveTokenId = 50L;
        User user = new User(userId, "1234", "향해99");
        User finduser = new User(findUserId, "123456", "향해오만번함");

        TokenStatus findTokenStatus = TokenStatus.ACTIVE;

        Token findToken = new Token(findTokenId,"topTokenId",finduser,"url",LocalDateTime.now()
                ,findTokenStatus,LocalDateTime.now(),LocalDateTime.now());

        //이미 등록 되어 있음
        doReturn(findToken).when(tokenRepository).findByUserIdAndStatus(any(Long.class),any(TokenStatus.class));

        //findToken에서는 필요가 없음
        List<Token> tokens = new ArrayList<>();
        doReturn(tokens).when(tokenRepository).findAllByStatus(TokenStatus.ACTIVE);

        //when
        TokenCommand.TokenCreateResDto TokenResDto = tokenService.createToken(userId);

        //then
        //assertEquals(findUserId,TokenResDto.userId());
        //assertEquals(TokenStatus.ACTIVE,TokenResDto.status());
        assertEquals(0L, TokenResDto.order());
        */
    }


    @Test
    @DisplayName("토큰 상태에 따른 만료 시간 - Wait")
    void isTokenExpired_Wait(){
        Long tokenId = 1L;
        LocalDateTime accessTime = LocalDateTime.of(2024,7,13,0,40);
        TokenStatus tokenStatus = TokenStatus.WAIT;
        Token token = new Token(tokenId,"tokenUUID",null,"url",accessTime,tokenStatus,null,null);

        //boolean tokenExpired = tokenService.isTokenExpired(token);

        //assertEquals(true,tokenExpired);
    }

    @Test
    @DisplayName("토큰 상태에 따른 만료 시간 - Active")
    void isTokenExpired_Active(){
        Long tokenId = 1L;
        LocalDateTime updateTime = LocalDateTime.of(2024,7,13,0,40);
        TokenStatus tokenStatus = TokenStatus.ACTIVE;
        Token token = new Token(tokenId,"tokenUUID",null,"url",null,tokenStatus,null,updateTime);

        //boolean tokenExpired = tokenService.isTokenExpired(token);

        //assertEquals(true,tokenExpired);
    }

    @Test
    @DisplayName("토큰 순번 테스트")
    void findOrder_test() {

        Long topTokenId = 31L;
        Long saveTokenId = 50L;
        Token token = new Token(saveTokenId,"saveTokenId",null,"url",LocalDateTime.now(),TokenStatus.WAIT,LocalDateTime.now(),LocalDateTime.now());
        Token topToken = new Token(topTokenId,"topTokenId",null,"url",LocalDateTime.now(),TokenStatus.WAIT,LocalDateTime.now(),LocalDateTime.now());
        doReturn(topToken).when(tokenRepository).findTopStatusWait();

        //long order = tokenService.findOrder(token);

        //assertEquals(saveTokenId - topTokenId,order);
    }





}