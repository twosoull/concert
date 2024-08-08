package io.hhplus.concert.presentation.dto;

import io.hhplus.concert.common.enums.ReservationStatus;
import io.hhplus.concert.common.enums.SeatStatus;
import io.hhplus.concert.domain.command.PaymentCommand;

import java.time.LocalDateTime;

public class PaymentDto {

    public record PaymentRequest(Long userId, Long concertScheduleId, Long concertReservationId, Long seatId){}
    public record PaymentResponse(String concertTitle,
                                String description,
                                LocalDateTime concertAt,
                                ReservationStatus reservationStatus,
                                Long price,
                                Long balance,
                                LocalDateTime PaymentTime,
                                Long seat,
                                SeatStatus seatStatus){}

    public static PaymentResponse paymentResponseOf(PaymentCommand.getPaymentInfo info){
        return new PaymentResponse(info.concertTitle(),
                info.description(),
                info.concertAt(),
                info.reservationStatus(),
                info.price(),
                info.balance(),
                info.PaymentTime(),
                info.seat(),
                info.seatStatus());
    }

    public static PaymentCommand.Pay paymentReqToCommend(PaymentRequest paymentRequest) {
        return new PaymentCommand.Pay(paymentRequest.concertScheduleId(),
                paymentRequest.concertReservationId(), paymentRequest.seatId());
    }
}
