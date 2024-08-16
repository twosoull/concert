package io.hhplus.concert.domain.command;

import io.hhplus.concert.common.enums.ReservationStatus;
import io.hhplus.concert.domain.entity.ConcertReservation;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;

public class ReservationCommand {

    public record Reservation(Long concertId
            , Long concertScheduleId
            , Long concertSeatId
            , Long userId){
    }

    public record Reserved(
            Long concertReservationId
            , Long concertScheduleId
            , Long concertId
            , Long userId
            , String concertTitle
            , String description
            , LocalDateTime concertAt
            , ReservationStatus status
            , Long price
            , LocalDateTime createAt
    ){
    }
    public static Reserved of(ConcertReservation cr){
        return new Reserved(cr.getConcertReservationId(),
                cr.getConcertSchedule().getConcertScheduleId(),
                cr.getConcert().getConcertId(),
                cr.getUser().getId(),
                cr.getConcertTitle(),
                cr.getDescription(),
                cr.getConcertAt(),
                cr.getStatus(),
                cr.getPrice(),
                cr.getCreateAt());
    }

}

