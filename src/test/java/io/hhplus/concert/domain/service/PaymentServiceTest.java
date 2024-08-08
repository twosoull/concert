package io.hhplus.concert.domain.service;

import io.hhplus.concert.common.enums.ReservationStatus;
import io.hhplus.concert.common.enums.SeatStatus;
import io.hhplus.concert.common.enums.TokenStatus;
import io.hhplus.concert.domain.command.PaymentCommand;
import io.hhplus.concert.domain.entity.*;
import io.hhplus.concert.domain.respository.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class PaymentServiceTest {

    @InjectMocks
    PaymentService paymentService;

    @Mock
    WalletRepository walletRepository;
    @Mock
    WalletHistoryRepository walletHistoryRepository;
    @Mock
    ConcertReservationRepository concertReservationRepository;
    @Mock
    ConcertSeatRepository concertSeatRepository;
    @Mock
    TokenRepository tokenRepository;


    @Test
    @DisplayName("결제 테스트")
    void payment_test() {
        //given
        Long userId = 1L;
        Long concertScheduleId = 1L;
        Long concertReservationId = 1L;
        Long walletId = 1L;
        Long seatId = 1L;
        Long price = 10000L;
        Long balance = 15000L;
        Long seat = 1L;
        SeatStatus seatStatus = SeatStatus.TEMP;
        String concertTitle = "향해가 좋아요";
        String description = "끝까지 가보자";
        LocalDateTime concertAt = LocalDateTime.of(2024,07, 14,13,10);
        ReservationStatus reservationStatus = ReservationStatus.TEMP;
        LocalDateTime tempAssignTime = LocalDateTime.of(2024,07, 14,13,6);
        LocalDateTime tokenAccessTime = LocalDateTime.of(2024,07, 14,6,6);
        LocalDateTime tokenUpdateTime = LocalDateTime.of(2024,07, 14,6,6);
        LocalDateTime now = LocalDateTime.now();

        User user               = new User(userId, "password", "향해하는 남자");
        Wallet wallet           = new Wallet(walletId, user, balance);
        ConcertReservation concertReservation
                                = new ConcertReservation(concertReservationId, null, null, user, concertTitle, description, concertAt
                                                                       , reservationStatus, price, now, null);
        ConcertSeat concertSeat = new ConcertSeat(seatId, null, null, seat, seatStatus, tempAssignTime, null);
        Token token             = new Token("토큰UUID", user, "이동 url", tokenAccessTime, TokenStatus.ACTIVE,tokenAccessTime, tokenUpdateTime);

        doReturn(wallet)
                       .when(walletRepository).findByUserId(userId);
        doReturn(concertReservation)
                       .when(concertReservationRepository).findById(concertReservationId);
        doReturn(concertSeat)
                       .when(concertSeatRepository).findById(seatId);
        doReturn(token).when(tokenRepository).findByUserId(userId);

        PaymentCommand.Pay Pay
                                 = new PaymentCommand.Pay(concertScheduleId, concertReservationId, seatId);

        PaymentCommand.getPaymentInfo paymentResDto = paymentService.pay(Pay);

        assertEquals(concertTitle, paymentResDto.concertTitle());
        assertEquals(concertAt, paymentResDto.concertAt());
        assertEquals(ReservationStatus.RESERVED, paymentResDto.reservationStatus());
        assertEquals(price, paymentResDto.price());
        assertEquals(SeatStatus.ASSIGNED, paymentResDto.seatStatus());

    }
}