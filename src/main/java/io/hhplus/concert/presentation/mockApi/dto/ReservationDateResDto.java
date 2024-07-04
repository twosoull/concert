package io.hhplus.concert.presentation.mockApi.dto;

import java.time.LocalDateTime;

public class ReservationDateResDto {

    private Long concertScheduleId;
    private Long concertId;
    private LocalDateTime concertAt;
    private int totalAvailable;
    private int totalReserved;
    private int price;

    public ReservationDateResDto(Long concertScheduleId, Long concertId, LocalDateTime concertAt, int totalAvailable, int totalReserved, int price) {
        this.concertScheduleId = concertScheduleId;
        this.concertId = concertId;
        this.concertAt = concertAt;
        this.totalAvailable = totalAvailable;
        this.totalReserved = totalReserved;
        this.price = price;
    }
}
