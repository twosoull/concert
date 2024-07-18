package io.hhplus.concert.domain.entity;

import io.hhplus.concert.common.enums.ReservationStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Builder
@Getter
@AllArgsConstructor
public class ConcertReservation {

    @Id @GeneratedValue
    @Column(name = "concert_reservation_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name="concert_schedule_id")
    private ConcertSchedule concertSchedule;

    @ManyToOne
    @JoinColumn(name = "concert_id")
    private Concert concert;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String concertTitle;
    private String description;
    private LocalDateTime concertAt;

    @Enumerated(EnumType.STRING)
    private ReservationStatus status;

    private Long price;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;

    public void setStatus(ReservationStatus status) {
        this.status = status;
    }

    public ConcertReservation() {
    }

    public static ConcertReservation reserveStatusTemp(ConcertSchedule concertSchedule, Concert concert, User user, String concertTitle, String description, LocalDateTime concertAt, Long price, LocalDateTime createAt){
        return new ConcertReservation().builder()
                .concertSchedule(concertSchedule)
                .concert(concert)
                .user(user)
                .concertTitle(concertTitle)
                .description(description)
                .concertAt(concertAt)
                .price(price)
                .createAt(createAt)
                .status(ReservationStatus.TEMP)
                .build();
    }

    public void setReserved() {
        if(this.status == ReservationStatus.RESERVED){
            throw new RuntimeException("이미 예약 된 좌석 입니다.");
        }

        this.status = ReservationStatus.RESERVED;
    }

}
