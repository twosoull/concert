package io.hhplus.concert.infrastructure.repositoryORM;

import io.hhplus.concert.common.enums.SeatStatus;
import io.hhplus.concert.domain.entity.ConcertSeat;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ConcertSeatJpaRepository extends JpaRepository<ConcertSeat,Long> {

    @Query("select s from ConcertSeat s where s.concertSchedule.id = :concertScheduleId and s.status = :status")
    List<ConcertSeat> findAllByConcertScheduleIdAndStatus(@Param("concertScheduleId") Long concertScheduleId, @Param("status")SeatStatus status);

    @Lock(LockModeType.OPTIMISTIC)
    @Query("select s from ConcertSeat s where s.id = :id")
    Optional<ConcertSeat> findByIdWithOptimisticLock(@Param("id") Long id);

    @Lock(LockModeType.PESSIMISTIC_READ)
    @Query("select s from ConcertSeat s where s.id = :id")
    Optional<ConcertSeat> findByIdWithPessimisticLock(@Param("id") Long id);


}
