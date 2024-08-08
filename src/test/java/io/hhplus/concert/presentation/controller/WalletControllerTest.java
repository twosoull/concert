package io.hhplus.concert.presentation.controller;

import io.hhplus.concert.domain.entity.User;
import io.hhplus.concert.domain.entity.Wallet;
import io.hhplus.concert.infrastructure.repositoryORM.WalletJpaRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class WalletControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    WalletJpaRepository walletJpaRepository;

    @Test
    void balanceInfo() throws Exception {

        User user = new User(1L,"1234","asdas");

        walletJpaRepository.save(new Wallet(1L,user,2000L));

        String json = "{ \"userId\" : 1 }";

        this.mockMvc.perform(get("/wallet/balance/info")
                        .content(json).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());

    }

    @Test
    void charge() throws Exception {

        User user = new User(1L,"1234","asdas");

        walletJpaRepository.save(new Wallet(1L,user,2000L));

        String json = "{ \"userId\" : 1 , \"amount\" : 3000}";

        this.mockMvc.perform(post("/wallet/charge")
                        .content(json).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());

    }
}