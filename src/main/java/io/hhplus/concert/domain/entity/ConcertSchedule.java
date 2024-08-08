package io.hhplus.concert.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Getter
public class ConcertSchedule {

    @Id @GeneratedValue
    @Column(name = "concert_schedule_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "concert_id")
    private Concert concert;

    private LocalDateTime concertAt; // 콘서트_일자
    private Long totalAvailable; //예약_가능_수

    private Long price;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;

    public ConcertSchedule() {
    }

    public ConcertSchedule(Concert concert, LocalDateTime concertAt, Long totalAvailable, Long price, LocalDateTime createAt, LocalDateTime updateAt) {
        this.concert = concert;
        this.concertAt = concertAt;
        this.totalAvailable = totalAvailable;
        this.price = price;
        this.createAt = createAt;
        this.updateAt = updateAt;
    }

    public ConcertSchedule(Long id, Concert concert, LocalDateTime concertAt, Long totalAvailable, Long price, LocalDateTime createAt, LocalDateTime updateAt) {
        this.id = id;
        this.concert = concert;
        this.concertAt = concertAt;
        this.totalAvailable = totalAvailable;
        this.price = price;
        this.createAt = createAt;
        this.updateAt = updateAt;
    }
}
