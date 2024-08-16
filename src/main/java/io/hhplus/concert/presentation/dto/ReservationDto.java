package io.hhplus.concert.presentation.dto;

import io.hhplus.concert.domain.entity.ConcertSchedule;

import java.time.LocalDateTime;

public class ReservationDto {

    public record ReservationRequest (Long concertId, Long concertScheduleId, Long concertSeatId, Long userId){}
    public record ReservationResponse () {}
    public record AvailableDateRequest (Long concertScheduleId){}
    public record AvailableSeatRequest  (Long concertScheduleId){}
    public record AvailableDateResponse (Long concertScheduleId, LocalDateTime concertAt) {}
    public record AvailableSeatResponse  (Long concertScheduleId
            ,Long concertSeatId
            ,Long seat){}


}
