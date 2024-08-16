package io.hhplus.concert.infrastructure.repositoryImpl;

import io.hhplus.concert.domain.entity.ConcertSchedule;
import io.hhplus.concert.domain.handler.exception.RestApiException;
import io.hhplus.concert.domain.handler.exception.errorCode.CommonErrorCode;
import io.hhplus.concert.domain.respository.ConcertScheduleRepository;
import io.hhplus.concert.infrastructure.repositoryORM.ConcertJpaRepository;
import io.hhplus.concert.infrastructure.repositoryORM.ConcertScheduleJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static io.hhplus.concert.domain.handler.exception.errorCode.CommonErrorCode.CONCERT_DATE_NOT_FOUND;
import static io.hhplus.concert.domain.handler.exception.errorCode.CommonErrorCode.RESOURCE_NOT_FOUND;

@Repository
@RequiredArgsConstructor
public class ConcertScheduleRepositoryImpl implements ConcertScheduleRepository {

    private final ConcertScheduleJpaRepository concertScheduleJpaRepository;

    @Override
    public ConcertSchedule save(ConcertSchedule concertSchedule) {
        return concertScheduleJpaRepository.save(concertSchedule);
    }

    @Override
    public List<ConcertSchedule> findByConcertId(Long concertId) {
        return concertScheduleJpaRepository.findByConcertId(concertId);
    }

    @Override
    public ConcertSchedule findById(Long concertScheduleId) {
        return concertScheduleJpaRepository.findById(concertScheduleId).orElseThrow(
                () -> new RestApiException(CONCERT_DATE_NOT_FOUND)
        );
    }
}
