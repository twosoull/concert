package io.hhplus.concert.domain.respository;

import io.hhplus.concert.domain.entity.ConcertSchedule;

import java.util.List;

public interface ConcertScheduleRepository {
    ConcertSchedule save(ConcertSchedule concertSchedule);

    List<ConcertSchedule> findByConcertId(Long concertId);

    ConcertSchedule findById(Long concertScheduleId);
}
