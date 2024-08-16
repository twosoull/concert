package io.hhplus.concert.infrastructure.repositoryImpl;

import io.hhplus.concert.common.enums.SeatStatus;
import io.hhplus.concert.domain.entity.ConcertSeat;
import io.hhplus.concert.domain.handler.exception.RestApiException;
import io.hhplus.concert.domain.respository.ConcertSeatRepository;
import io.hhplus.concert.infrastructure.repositoryORM.ConcertSeatJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static io.hhplus.concert.domain.handler.exception.errorCode.CommonErrorCode.RESOURCE_NOT_FOUND;

@Repository
@RequiredArgsConstructor
public class ConcertSeatRepositoryImpl implements ConcertSeatRepository {

    private final ConcertSeatJpaRepository concertSeatJpaRepository;

    @Override
    public List<ConcertSeat> findAllByConcertScheduleIdAndStatus(Long concertScheduleId, SeatStatus status) {
        return concertSeatJpaRepository.findAllByConcertScheduleIdAndStatus(concertScheduleId, status);
    }

    @Override
    public ConcertSeat findById(Long concertSeatId) {
        return concertSeatJpaRepository.findById(concertSeatId).orElseThrow(
                () -> new RestApiException(RESOURCE_NOT_FOUND)
        );
    }

    public ConcertSeat save(ConcertSeat concertSeat) {
        return concertSeatJpaRepository.save(concertSeat);
    }
}
