package io.hhplus.concert.presentation.controller;

import io.hhplus.concert.common.enums.TokenStatus;
import io.hhplus.concert.domain.command.TokenCommand;
import io.hhplus.concert.domain.service.TokenService;
import io.hhplus.concert.presentation.dto.TokenDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class TokenControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    @DisplayName("토큰 생성 후 레디스에 저장")
    void createToken() throws Exception {

        String jsonRequest = "{\"userId\": \"1\"}";

        this.mockMvc.perform(post("/api/token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("레디스 토큰 조회 - 대기열 토큰인 경우")
    void getTokenInfo_waiting() throws Exception {

        String currentToken = "1ff9781c-0191-1000-9c82-7dd01ef702b2";
        this.mockMvc.perform(get("/api/token/info")
                        .header("Authorization", "Bearer " + currentToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());

    }

    @Test
    @DisplayName("레디스 토큰 조회 - 액티브 토큰인 경우")
    void getTokenInfo_active() throws Exception {

        String currentToken = "asdf";
        this.mockMvc.perform(get("/api/token/info")
                        .header("Authorization", "Bearer " + currentToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());

    }

    @Test
    void info_Wait() throws Exception {
        /*
        Long id = 1L;
        Long userId = 1L;
        Long order = 30L;
        LocalDateTime accessTime = LocalDateTime.of(2024,07,17,22,30);
        TokenStatus status = TokenStatus.WAIT;

        TokenDto.infoTokenResponse mockResponse = new TokenDto.infoTokenResponse(id
        ,userId,order, accessTime, status);


        String mockToken = "Bearer mock-token";
        TokenCommand.TokenCreateResDto tokenCreateResDto = new TokenCommand.TokenCreateResDto(id, userId, mockToken, order, accessTime, status);
        Mockito.when(tokenService.getTokenInfo(anyString())).thenReturn(tokenCreateResDto);

        mockMvc.perform(get("/api/token/info")
                        .header("Authorization", mockToken)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                //.andExpect(content().json("{\"info\":\"mockInfo\"}"))
                .andDo(print());
                */

    }

    @Test
    void info_Active() throws Exception {
        //data상으로 27번까지 밀어놓은 상태입니다.
        String json = "{ \"userId\" : 20 }";

        this.mockMvc.perform(get("/api/token/info")
                        .content(json).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());

    }
}