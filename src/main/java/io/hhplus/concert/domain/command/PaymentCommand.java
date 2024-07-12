package io.hhplus.concert.domain.command;

import io.hhplus.concert.common.enums.ReservationStatus;
import io.hhplus.concert.common.enums.SeatStatus;
import io.hhplus.concert.domain.entity.Wallet;
import io.hhplus.concert.domain.entity.WalletHistory;

import java.time.LocalDateTime;

public class PaymentCommand {

    public record PaymentReqDto(Long userId, Long concertScheduleId, Long concertReservationId, Long seatId){}
    public record PaymentResDto(String ConcertTitle,
                                String description,
                                LocalDateTime concertAt,
                                ReservationStatus reservationStatus,
                                Long price,
                                LocalDateTime PaymentTime,
                                Long seat,
                                SeatStatus seatStatus){}


    /*
    public static PaymentCommand.PaymentResDto walletOf(Wallet wallet) {
        return new PaymentResDto(wallet.getId(),
                wallet.getUser().getId()
                ,wallet.getBalance());
    }*/


}
