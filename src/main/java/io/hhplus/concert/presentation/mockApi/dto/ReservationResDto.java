package io.hhplus.concert.presentation.mockApi.dto;

import java.time.LocalDateTime;

public class ReservationResDto {

    private Long concertReservationId;
    private String concertTitle;
    private String description;
    private LocalDateTime concertAt;
    private int reservedSeats;
    private Long price;
    private LocalDateTime createAt;

    public ReservationResDto(Long concertReservationId, String concertTitle, String description, LocalDateTime concertAt, int reservedSeats, Long price, LocalDateTime createAt) {
        this.concertReservationId = concertReservationId;
        this.concertTitle = concertTitle;
        this.description = description;
        this.concertAt = concertAt;
        this.reservedSeats = reservedSeats;
        this.price = price;
        this.createAt = createAt;
    }
}
