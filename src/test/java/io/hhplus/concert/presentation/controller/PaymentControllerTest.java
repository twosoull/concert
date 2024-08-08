package io.hhplus.concert.presentation.controller;

import io.hhplus.concert.common.enums.SeatStatus;
import io.hhplus.concert.domain.entity.*;
import io.hhplus.concert.infrastructure.repositoryORM.ConcertReservationJpaRepository;
import io.hhplus.concert.infrastructure.repositoryORM.WalletJpaRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class PaymentControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    WalletJpaRepository walletJpaRepository;

    @Autowired
    ConcertReservationJpaRepository concertReservationJpaRepository;

    @Test
    @DisplayName("결제- 잔고부족")
    void payment_fail() throws Exception {

        Long userId = 1L;
        Long concertId = 1L;
        Long concertScheduleId = 1L;
        Long concertSeatId = 1L;
        Long balance = 2000L;
        Long price = 3000L;

        User user = new User(userId,"1234","asdas");
        walletJpaRepository.save(new Wallet(1L,user,balance));

        Concert concert = new Concert(concertId, "향해하는 남자", "욕심은 버려라, 할 수 있는 만큼만 해보자");

        ConcertSchedule concertSchedule = new ConcertSchedule(concertScheduleId, concert, LocalDateTime.now(),100L
                ,price,LocalDateTime.now(),LocalDateTime.now());
        ConcertSeat concertSeat = new ConcertSeat(concertSeatId, concertSchedule,
                null, 1L, SeatStatus.UNASSIGNED, null,null);

        ConcertReservation concertReservation = ConcertReservation.createReserveStatusTemp(concertSchedule, user, LocalDateTime.now());
        ConcertReservation saveReservation = concertReservationJpaRepository.save(concertReservation);

        String json = "{ \"userId\" : "+userId+", \"concertScheduleId\" : "+
                concertScheduleId+", \"concertReservationId\" : "+saveReservation.getId()+", \"seatId\" : "+concertSeatId+" }";

        this.mockMvc.perform(post("/payment")
                        .content(json).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());


    }

    @Test
    @DisplayName("결제- 성공")
    void payment_success() throws Exception {

        Long userId = 1L;
        Long concertId = 1L;
        Long concertScheduleId = 1L;
        Long concertSeatId = 1L;
        Long balance = 4000L;
        Long price = 3000L;

        User user = new User(userId,"1234","asdas");
        walletJpaRepository.save(new Wallet(1L,user,balance));

        Concert concert = new Concert(concertId, "향해하는 남자", "욕심은 버려라, 할 수 있는 만큼만 해보자",
                LocalDateTime.of(2024,07,15,13,10));

        ConcertSchedule concertSchedule = new ConcertSchedule(concertScheduleId, concert, LocalDateTime.now(),100L
                ,price,LocalDateTime.now(),LocalDateTime.now());
        ConcertSeat concertSeat = new ConcertSeat(concertSeatId, concertSchedule,
                null, 1L, SeatStatus.UNASSIGNED, null,null);

        ConcertReservation concertReservation = ConcertReservation.createReserveStatusTemp(concertSchedule, user, LocalDateTime.now());
        ConcertReservation saveReservation = concertReservationJpaRepository.save(concertReservation);

        String json = "{ \"userId\" : "+userId+", \"concertScheduleId\" : "+
                concertScheduleId+", \"concertReservationId\" : "+saveReservation.getId()+", \"seatId\" : "+concertSeatId+" }";

        this.mockMvc.perform(post("/payment")
                        .content(json).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());

    }
}