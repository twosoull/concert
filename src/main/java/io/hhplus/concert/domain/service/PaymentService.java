package io.hhplus.concert.domain.service;

import io.hhplus.concert.domain.command.PaymentCommand;
import io.hhplus.concert.domain.entity.*;
import io.hhplus.concert.domain.respository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
public class PaymentService {

    private final WalletRepository walletRepository;
    private final WalletHistoryRepository walletHistoryRepository;
    private final ConcertReservationRepository concertReservationRepository;
    private final ConcertSeatRepository concertSeatRepository;
    private final TokenRepository tokenRepository;


    public PaymentCommand.getPaymentInfo pay(PaymentCommand.Pay pay) {
        LocalDateTime now = LocalDateTime.now();
        Wallet wallet = walletRepository.findByUserId(pay.userId());
        ConcertReservation concertReservation = concertReservationRepository.findById(pay.concertReservationId());
        ConcertSeat concertSeat = concertSeatRepository.findById(pay.seatId());
        Token token = tokenRepository.findByUserId(pay.userId());

        //예약했던 콘서트일정 상태를 확정으로 만들어 준다.
        concertReservation.setReserved();
        // 콘서트 자리 확정으로 해준다.
        concertSeat.seatStatusAssign(now);

        // 토큰을 만료한다.
        token.expiration(now);

        //콘서트 금액과 잔고의 금액을 마이너스 해준다.
        Long price = concertReservation.getPrice();
        Long balanceBefore = wallet.getBalance();
        Long balanceAfter = wallet.usePoint(price, now);

        // 히스토리를 쌓는다.
        walletHistoryRepository.save(getWalletHistory(wallet, price, balanceBefore, balanceAfter, now));

        return new PaymentCommand.getPaymentInfo(concertReservation.getConcertTitle(),
                                                concertReservation.getDescription(),
                                                concertReservation.getConcertAt(),
                                                concertReservation.getStatus(),
                                                price,
                                                balanceAfter,
                                                wallet.getLastUpdateAt(),
                                                concertSeat.getSeat(),
                                                concertSeat.getStatus());
    }


    private static WalletHistory getWalletHistory(Wallet wallet, Long amount, Long balanceBefore,Long balanceAfter, LocalDateTime now) {
        WalletHistory walletHistory = WalletHistory.useHistory(
                wallet
                , amount
                , balanceBefore
                , balanceAfter
                , now
        );
        return walletHistory;
    }
}
