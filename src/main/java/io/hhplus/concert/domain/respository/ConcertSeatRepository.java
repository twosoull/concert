package io.hhplus.concert.domain.respository;


import io.hhplus.concert.common.enums.SeatStatus;
import io.hhplus.concert.domain.entity.ConcertSchedule;
import io.hhplus.concert.domain.entity.ConcertSeat;

import java.util.List;

public interface ConcertSeatRepository {
    List<ConcertSeat> findAllByConcertScheduleIdAndStatus(Long concertScheduleId, SeatStatus status);

    ConcertSeat findById(Long aLong);

    ConcertSeat findByIdWithOptimisticLock(Long aLong);

    ConcertSeat findByIdWithPessimisticLock(Long aLong);

    void saveAll(List<ConcertSeat> concertSeats );
}
