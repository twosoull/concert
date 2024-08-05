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

    @Version
    private Long version;


    public void setStatus(ReservationStatus status) {
        this.status = status;
    }

    public ConcertReservation() {
    }

    public ConcertReservation(Long concertReservationId, Object o, Object o1, User user, String concertTitle, String description, LocalDateTime concertAt, ReservationStatus reservationStatus, Long price, LocalDateTime now, Object o2) {
    }

    public static ConcertReservation createReserveStatusTemp(ConcertSchedule concertSchedule,User user, LocalDateTime now){
        return new ConcertReservation().builder()
                .concertSchedule(concertSchedule)
                .concert(concertSchedule.getConcert())
                .user(user)
                .concertTitle(concertSchedule.getConcert().getConcertTitle())
                .description(concertSchedule.getConcert().getDescription())
                .concertAt(concertSchedule.getConcertAt())
                .price(concertSchedule.getPrice())
                .createAt(now)
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
