package io.hhplus.concert.domain.message;

import lombok.Builder;
import lombok.Data;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@Builder
public class PaymentMessage {

    private Long concertReservationId;
    private Long concertSeatId;
    private String reservationTime;
    private Long outboxId;

    public PaymentMessage() {
    }

    public PaymentMessage(Long concertReservationId, Long concertSeatId, String reservationTime) {
        this.concertReservationId = concertReservationId;
        this.concertSeatId = concertSeatId;
        this.reservationTime = reservationTime;
    }

    public PaymentMessage(Long concertReservationId, Long concertSeatId, String reservationTime, Long outboxId) {
        this.concertReservationId = concertReservationId;
        this.concertSeatId = concertSeatId;
        this.reservationTime = reservationTime;
        this.outboxId = outboxId;
    }

    public LocalDateTime convertReservationTime(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime convertReservationTime = LocalDateTime.parse(this.reservationTime, formatter);
        return convertReservationTime;
    };


    public static PaymentMessage getMessage(Long concertReservationId, Long concertSeatId, LocalDateTime reservationTime,Long outboxId) {
        String formattedReservationTime = reservationTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        return PaymentMessage
                .builder()
                .concertReservationId(concertReservationId)
                .concertSeatId(concertSeatId)
                .reservationTime(formattedReservationTime)
                .outboxId(outboxId)
                .build();
    }


}
