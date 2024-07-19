package io.hhplus.concert.infrastructure.repositoryORM;

import io.hhplus.concert.common.enums.SeatStatus;
import io.hhplus.concert.domain.entity.ConcertSeat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ConcertSeatJpaRepository extends JpaRepository<ConcertSeat,Long> {

    @Query("select s from ConcertSeat s where s.concertSchedule.id = :concertScheduleId and s.status = :status")
    List<ConcertSeat> findAllByConcertScheduleIdAndStatus(@Param("concertScheduleId") Long concertScheduleId, @Param("status")SeatStatus status);
}
