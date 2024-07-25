package io.hhplus.concert.presentation.mockApi.dto;

import java.time.LocalDateTime;

public class ReservationSeatResDto {

    private Long concertSeatId;
    private Long concertId;
    private int seat;
    private String status;
    private LocalDateTime updateAt;

    public ReservationSeatResDto(Long concertSeatId, Long concertId, int seat, String status, LocalDateTime updateAt) {
        this.concertSeatId = concertSeatId;
        this.concertId = concertId;
        this.seat = seat;
        this.status = status;
        this.updateAt = updateAt;
    }
}
