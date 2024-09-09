package io.hhplus.concert.domain.command;

import io.hhplus.concert.common.enums.ReservationStatus;
import io.hhplus.concert.common.enums.SeatStatus;
import io.hhplus.concert.domain.entity.Wallet;
import io.hhplus.concert.domain.entity.WalletHistory;

import java.time.LocalDateTime;

public class PaymentCommand {
    public record Pay(Long concertScheduleId, Long concertReservationId, Long seatId){
    }
    public record getPaymentInfo(String concertTitle,
                                String description,
                                LocalDateTime concertAt,
                                ReservationStatus reservationStatus,
                                Long price,
                                Long balance,
                                LocalDateTime PaymentTime,
                                Long seat,
                                SeatStatus seatStatus){}

}
