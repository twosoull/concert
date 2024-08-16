package io.hhplus.concert.presentation.controller;

import io.hhplus.concert.common.enums.TokenStatus;
import io.hhplus.concert.domain.command.TokenCommand;
import io.hhplus.concert.domain.service.TokenService;
import io.hhplus.concert.presentation.dto.TokenDto;
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

    @MockBean
    private TokenService tokenService;

    @Test
    void create_insert() throws Exception {

        String json = "{ \"userId\" : 100 }";

        this.mockMvc.perform(post("/api/token/create")
                        .content(json).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());

    }

    @Test
    void create_find() throws Exception {
        //data상으로 27번까지 밀어놓은 상태입니다.
        String json = "{ \"userId\" : 26 }";

        this.mockMvc.perform(post("/api/token/create")
                        .content(json).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());

    }

    @Test
    void info_Wait() throws Exception {
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