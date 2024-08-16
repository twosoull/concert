package io.hhplus.concert.domain.event;

import io.hhplus.concert.common.enums.SeatStatus;
import org.springframework.context.ApplicationEvent;

import java.time.LocalDateTime;

public class PaymentEvent extends ApplicationEvent {

    private final Long concertReservationId;
    private final Long concertSeatId;
    private final LocalDateTime reservationTime;
    private Long outboxId;

    public PaymentEvent(Object source, Long concertReservationId, Long concertSeatId, LocalDateTime reservationTime, Long outboxId) {
        super(source);
        this.concertReservationId = concertReservationId;
        this.concertSeatId = concertSeatId;
        this.reservationTime = reservationTime;
        this.outboxId = outboxId;
    }

    public Long getConcertReservationId() {
        return concertReservationId;
    }

    public Long getConcertSeatId() {
        return concertSeatId;
    }

    public LocalDateTime getReservationTime() {
        return reservationTime;
    }

    public Long getOutboxId() {
        return outboxId;
    }

    public void setOutboxId(Long outboxId){
        this.outboxId = outboxId;
    }

}
