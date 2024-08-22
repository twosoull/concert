package io.hhplus.concert.domain.command;

import io.hhplus.concert.common.enums.ReservationStatus;
import io.hhplus.concert.domain.entity.ConcertReservation;

import java.time.LocalDateTime;

public class ReservationCommand {

    public record reserve(Long concertId
            , Long concertScheduleId
            , Long concertSeatId
            , String LockType){
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
        return new Reserved(cr.getId(),
                cr.getConcertSchedule().getId(),
                cr.getConcert().getId(),
                cr.getUser().getId(),
                cr.getConcertTitle(),
                cr.getDescription(),
                cr.getConcertAt(),
                cr.getStatus(),
                cr.getPrice(),
                cr.getCreateAt());
    }

}

