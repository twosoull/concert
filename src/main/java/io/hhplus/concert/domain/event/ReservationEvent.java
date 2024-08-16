package io.hhplus.concert.domain.event;

import io.hhplus.concert.domain.entity.ConcertReservation;
import io.hhplus.concert.domain.entity.ConcertSeat;
import org.springframework.context.ApplicationEvent;

import java.time.LocalDateTime;

public class ReservationEvent extends ApplicationEvent {

    private final ConcertReservation concertReservation;
    private final ConcertSeat concertSeat;
    private final LocalDateTime reservationTime;


    public ReservationEvent(Object source, ConcertReservation concertReservation, ConcertSeat concertSeat, LocalDateTime reservationTime) {
        super(source);
        this.concertReservation = concertReservation;
        this.concertSeat = concertSeat;
        this.reservationTime = reservationTime;
    }

    public ConcertReservation getConcertReservation() {
        return concertReservation;
    }

    public ConcertSeat getConcertSeat() {
        return concertSeat;
    }

    public LocalDateTime getReservationTime() {
        return reservationTime;
    }
}
