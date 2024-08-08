package io.hhplus.concert.presentation.dto;

import io.hhplus.concert.common.enums.ReservationStatus;
import io.hhplus.concert.domain.command.ReservationCommand;
import io.hhplus.concert.domain.entity.ConcertReservation;
import io.hhplus.concert.domain.entity.ConcertSchedule;
import io.hhplus.concert.domain.entity.ConcertSeat;

import java.time.LocalDateTime;
import java.util.List;

public class ReservationDto {

    public record ReservationRequest (Long concertId, Long concertScheduleId, Long concertSeatId, String LockType){}

    public record AvailableDateResponse(Long concertScheduleId,LocalDateTime concertAt){}

    public record AvailableSeatResponse  (Long concertScheduleId
            ,Long concertSeatId
            ,Long seat){}

    public record ReservationResponse(
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
    ){}
    public static ReservationDto.AvailableDateResponse ResponseDateDto(ConcertSchedule concertSchedule){
        return new AvailableDateResponse(concertSchedule.getId(), concertSchedule.getConcertAt());
    }

    public static ReservationDto.AvailableSeatResponse ResponseSeatDto(ConcertSeat concertSeat){
        return new AvailableSeatResponse(concertSeat.getConcertSchedule().getId(), concertSeat.getId(),concertSeat.getSeat());
    }

    public static ReservationDto.ReservationResponse ResponseResevationDto(ConcertReservation concertReservation){
        return new ReservationDto.ReservationResponse(concertReservation.getId(),
                concertReservation.getConcertSchedule().getId(),
                concertReservation.getConcert().getId(),
                concertReservation.getUser().getId(),
                concertReservation.getConcertTitle(),
                concertReservation.getDescription(),
                concertReservation.getConcertAt(),
                concertReservation.getStatus(),
                concertReservation.getPrice(),
                concertReservation.getCreateAt());
    }

    public static ReservationCommand.reserve toCommand(ReservationRequest reservationRequest){
        return new ReservationCommand.reserve(reservationRequest.concertId,reservationRequest.concertScheduleId
        , reservationRequest.concertSeatId,"");
    }
}
