package io.hhplus.concert.presentation.mockApi.dto;

import java.time.LocalDateTime;

public class PaymentResDto {

    private Long price;
    private Long balance;
    private String concertTitle;
    private LocalDateTime creataAt;
    private int reservedSeats;

    public PaymentResDto(Long price, Long balance, String concertTitle, LocalDateTime creataAt, int reservedSeats) {
        this.price = price;
        this.balance = balance;
        this.concertTitle = concertTitle;
        this.creataAt = creataAt;
        this.reservedSeats = reservedSeats;
    }
}
