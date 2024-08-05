package io.hhplus.concert.domain.entity;

import io.hhplus.concert.common.enums.SeatStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class ConcertSeat {

    @Id
    @GeneratedValue
    @Column(name = "concert_seat_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "concert_schedule_id")
    private ConcertSchedule concertSchedule;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private Long seat;

    @Enumerated(EnumType.STRING)
    private SeatStatus status;

    private LocalDateTime tempAssignmentTime;
    private LocalDateTime assignmentTime;

    @Version
    private Long version;

    public void setConcertSchedule(ConcertSchedule concertSchedule) {
        this.concertSchedule = concertSchedule;
    }

    public void setStatus(SeatStatus status) {
        this.status = status;
    }

    public void setTempAssignmentTime(LocalDateTime tempAssignmentTime) {
        this.tempAssignmentTime = tempAssignmentTime;
    }

    public ConcertSeat(Long id, ConcertSchedule concertSchedule, User user, Long seat, SeatStatus status, LocalDateTime tempAssignmentTime, LocalDateTime assignmentTime) {
        this.id = id;
        this.concertSchedule = concertSchedule;
        this.user = user;
        this.seat = seat;
        this.status = status;
        this.tempAssignmentTime = tempAssignmentTime;
        this.assignmentTime = assignmentTime;
    }

    public ConcertSeat(ConcertSchedule concertSchedule, User user, Long seat, SeatStatus status, LocalDateTime tempAssignmentTime, LocalDateTime assignmentTime) {
        this.concertSchedule = concertSchedule;
        this.user = user;
        this.seat = seat;
        this.status = status;
        this.tempAssignmentTime = tempAssignmentTime;
        this.assignmentTime = assignmentTime;
    }


    public void setSeatStatusTemp(ConcertSchedule concertSchedule, User user, LocalDateTime now){
        this.concertSchedule = concertSchedule;
        this.status = SeatStatus.TEMP;
        this.user = user;
        this.tempAssignmentTime = now;
    }

    public void seatStatusAssign(LocalDateTime now){
        this.status = SeatStatus.ASSIGNED;
        this.assignmentTime = now;
    }
}