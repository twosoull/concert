package io.hhplus.concert.domain.respository;

import io.hhplus.concert.domain.entity.ConcertSchedule;

public interface ConcertScheduleRepository {
    ConcertSchedule save(ConcertSchedule concertSchedule);

    ConcertSchedule findById(Long concertScheduleId);
}
