package io.hhplus.concert.infrastructure.repositoryImpl;

import io.hhplus.concert.common.enums.SeatStatus;
import io.hhplus.concert.domain.entity.ConcertSeat;
import io.hhplus.concert.domain.respository.ConcertSeatRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ConcertSeatRepositoryImpl implements ConcertSeatRepository {
    @Override
    public List<ConcertSeat> findAllByConcertScheduleIdAndStatus(Long concertScheduleId, SeatStatus status) {
        return List.of();
    }

    @Override
    public ConcertSeat findById(Long aLong) {
        return null;
    }
}
